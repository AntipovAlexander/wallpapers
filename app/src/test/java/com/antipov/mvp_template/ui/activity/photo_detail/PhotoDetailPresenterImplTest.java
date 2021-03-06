package com.antipov.mvp_template.ui.activity.photo_detail;

import android.graphics.Bitmap;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PhotoDetailPresenterImplTest {

    @Mock
    PhotoDetailView mMockedView;

    @Mock
    PhotoDetailInteractor mMockedInteractor;

    @Mock
    WallPaperSetter mMockedWallpaperSetter;

    @Mock
    Bitmap bitmap;

    PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> mPresenter;

    @Before
    public void setUp() {
        mPresenter = new PhotoDetailPresenterImpl<>(mMockedInteractor, mMockedWallpaperSetter);
        mPresenter.attachView(mMockedView);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }


    /**
     * test error, while loading image from url via glide
     */
    @Test
    public void testLoadPictureFromUrlSuccess () {
        Picture picture = new Picture();
        doAnswer(invocation -> {
            mPresenter.onPictureLoaded();
            return null;
        }).when(mMockedView).renderLayout(picture);
        mPresenter.onViewPrepared(picture);
        verify(mMockedView).renderLayout(picture);
        verify(mMockedView).hideLoadingFullScreen();
        verifyNoMoreInteractions(mMockedView);
    }

    /**
     * test error, while loading image from url via glide
     */
    @Test
    public void testLoadPictureFromUrlFailure() {
        Picture picture = new Picture();
        doAnswer(invocation -> {
            mPresenter.onPictureNotLoaded("error message");
            return null;
        }).when(mMockedView).renderLayout(picture);
        mPresenter.onViewPrepared(picture);
        verify(mMockedView).renderLayout(picture);
        verify(mMockedView).hideLoadingFullScreen();
        verify(mMockedView).showFullScreenError(ArgumentMatchers.anyString());
        verifyNoMoreInteractions(mMockedView);
    }

    /**
     * Tests for method without wallpaper target flag (for device with api < 24)
     */

    @Test
    public void testSetupWallPaperSuccessSupport() {
        Picture mPicture = Picture.getForTests();
        IOnWallPaperChanged listener = (IOnWallPaperChanged) mPresenter;
        doAnswer(invocation -> {
            listener.onWallPaperChangedSuccess(mPicture);
            return null;
        }).when(mMockedWallpaperSetter).setWallPaper(bitmap, mPicture, listener);
        mPresenter.setWallPaper(bitmap, mPicture);
        verify(mMockedView).showLoading();
        verify(mMockedView).hideLoading();
        verify(mMockedView).showMessage(ArgumentMatchers.anyInt());
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void testSetupWallPaperErrorSupport() {
        Picture mPicture = Picture.getForTests();
        IOnWallPaperChanged listener = (IOnWallPaperChanged) mPresenter;
        doAnswer(invocation -> {
            listener.onWallPaperChangedFailure("err");
            return null;
        }).when(mMockedWallpaperSetter).setWallPaper(bitmap, mPicture, listener);
        mPresenter.setWallPaper(bitmap, mPicture);
        verify(mMockedView).showLoading();
        verify(mMockedView).hideLoading();
        verifyNoMoreInteractions(mMockedView);
    }

    /**
     * Tests for method with wallpaper target flag (for device with api >= 24)
     */

    @Test
    public void testSetupWallPaperSuccess() {
        Picture mPicture = Picture.getForTests();
        IOnWallPaperChanged listener = (IOnWallPaperChanged) mPresenter;
        doAnswer(invocation -> {
            listener.onWallPaperChangedSuccess(mPicture);
            return null;
        }).when(mMockedWallpaperSetter).setWallPaper(bitmap, mPicture, 0, listener);
        mPresenter.setWallPaper(bitmap, mPicture, 0);
        verify(mMockedView).showLoading();
        verify(mMockedView).hideLoading();
        verify(mMockedView).showMessage(ArgumentMatchers.anyInt());
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void testSetupWallPaperError() {
        Picture mPicture = Picture.getForTests();
        IOnWallPaperChanged listener = (IOnWallPaperChanged) mPresenter;
        doAnswer(invocation -> {
            listener.onWallPaperChangedFailure("err");
            return null;
        }).when(mMockedWallpaperSetter).setWallPaper(bitmap, mPicture, 0,listener);
        mPresenter.setWallPaper(bitmap, mPicture, 0);
        verify(mMockedView).showLoading();
        verify(mMockedView).hideLoading();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void onOpenInBrowserClicked() {
        mPresenter.onOpenInBrowserClicked();
        verify(mMockedView).startBrowserIntent();
        verifyNoMoreInteractions(mMockedView);
    }
}