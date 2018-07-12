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

import rx.Observable;

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
    public void setUp() throws Exception {
        mPresenter = new PhotoDetailPresenterImpl<>(mMockedInteractor, mMockedWallpaperSetter);
        mPresenter.attachView(mMockedView);
    }

    @After
    public void tearDown() throws Exception {
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

    @Test
    public void testSetupWallPaperSuccess() {
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
    public void testSetupWallPaperError() {
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
}