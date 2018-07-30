package com.antipov.mvp_template.ui.activity.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTestOnJobScheduled {

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
    public void testOnJobScheduledTrue() {
        mPresenter.onJobScheduled(true);
        verify(mMockedMainView).setIsScheduled();
        verifyNoMoreInteractions(mMockedMainView);
    }

    @Test
    public void testOnJobScheduledFalse() {
        mPresenter.onJobScheduled(false);
        verify(mMockedMainView).setIsNotScheduled();
        verifyNoMoreInteractions(mMockedMainView);
    }
}
