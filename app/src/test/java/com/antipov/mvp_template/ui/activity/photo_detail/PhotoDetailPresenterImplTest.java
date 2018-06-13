package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.pojo.Picture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        mPresenter.getPicture("");
        verify(mMockedView).showLoading();
        verify(mMockedView).renderLayout(picture);
        verify(mMockedView).hideLoading();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void getPictureError() {
        doReturn(Observable.just(new Exception())).when(mMockedInteractor).getPicture(ArgumentMatchers.anyString());
        mPresenter.getPicture("");
        verify(mMockedView).showLoading();
        verify(mMockedView).showFullScreenError(ArgumentMatchers.anyString());
        verify(mMockedView).hideLoading();
        verifyNoMoreInteractions(mMockedView);
    }
}