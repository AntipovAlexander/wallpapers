package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.pojo.Picture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import rx.Observable;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhotoDetailPresenterImplTest {

    @Mock
    PhotoDetailView mMockedView;

    @Mock
    PhotoDetailInteractor mMockedInteractor;

    PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new PhotoDetailPresenterImpl<>(mMockedInteractor);
        mPresenter.attachView(mMockedView);
    }

    @After
    public void tearDown() throws Exception {
        mPresenter.detachView();
    }

    @Test
    public void getPictureSuccess() {
        Picture picture = new Picture();
        doReturn(Observable.just(picture)).when(mMockedInteractor).getPicture(ArgumentMatchers.anyString());
        doAnswer(invocation -> {
            mPresenter.onPictureLoaded();
            return null;
        }).when(mMockedView).renderLayout(picture);
        mPresenter.getPicture("");
        verify(mMockedView).showLoadingFullScreen();
        verify(mMockedView).renderLayout(picture);
        verify(mMockedView).hideLoadingFullScreen();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void getPictureError() {
        doReturn(Observable.just(new Exception())).when(mMockedInteractor).getPicture(ArgumentMatchers.anyString());
        mPresenter.getPicture("");
        verify(mMockedView).showLoadingFullScreen();
        verify(mMockedView).showFullScreenError(ArgumentMatchers.anyString());
        verify(mMockedView).hideLoadingFullScreen();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void pictureCantBeLoaded() {
        Picture picture = new Picture();
        doReturn(Observable.just(picture)).when(mMockedInteractor).getPicture(ArgumentMatchers.anyString());
        doAnswer(invocation -> {
            mPresenter.onPictureNotLoaded(ArgumentMatchers.anyString());
            return null;
        }).when(mMockedView).renderLayout(picture);
        mPresenter.getPicture("");
        verify(mMockedView).showLoadingFullScreen();
        verify(mMockedView).renderLayout(picture);
        verify(mMockedView).hideLoadingFullScreen();
        verify(mMockedView).showFullScreenError(ArgumentMatchers.anyString());
        verifyNoMoreInteractions(mMockedView);
    }
}