package com.antipov.mvp_template.main_test.main;

import com.antipov.mvp_template.ui.activity.main.MainInteractor;
import com.antipov.mvp_template.ui.activity.main.MainPresenter;
import com.antipov.mvp_template.ui.activity.main.MainPresenterImpl;
import com.antipov.mvp_template.ui.activity.main.MainView;

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
public class MainPresenterTestFailure {
    @Mock
    MainView mMockedMainView;

    @Mock
    MainInteractor mMainInteractor;

    MainPresenter<MainView, MainInteractor> mPresenter;

    @Before
    public void setUp(){
        mPresenter = new MainPresenterImpl<>(mMainInteractor);
        mPresenter.attachView(mMockedMainView);
    }

    @Test
    public void testGetPicturesFailure() {
        doReturn(Observable.just(new Exception())).when(mMainInteractor).getPictures();
        mPresenter.getPictures();
        verify(mMockedMainView).showFullScreenError(ArgumentMatchers.anyString());
        verify(mMockedMainView).hideLoadingFullScreen();
        verifyNoMoreInteractions(mMockedMainView);
    }

    @After
    public void tearDown(){
        mPresenter.detachView();
    }
}
