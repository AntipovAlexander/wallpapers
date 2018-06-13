package com.antipov.mvp_template.main_test;

import com.antipov.mvp_template.main_test.rx.TestSchedulerProvider;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.main.MainInteractor;
import com.antipov.mvp_template.ui.activity.main.MainInteractorImpl;
import com.antipov.mvp_template.ui.activity.main.MainPresenter;
import com.antipov.mvp_template.ui.activity.main.MainPresenterImpl;
import com.antipov.mvp_template.ui.activity.main.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import rx.schedulers.TestScheduler;

/**
 * Created by AlexanderAntipov on 06.06.2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTestSuccess {
    @Mock
    MainView mMockedMainView;

    MainInteractor mMainInteractor;
    MainPresenter<MainView, MainInteractor> mPresenter;
    TestScheduler mTestScheduler;

    @Before
    public void setUp(){
        mTestScheduler = new TestScheduler();
        mMainInteractor = new MainInteractorImpl(new TestSchedulerProvider(mTestScheduler));
        mPresenter = new MainPresenterImpl<>(mMainInteractor);
        mPresenter.attachView(mMockedMainView);
    }

    @Test
    public void testGetPicturesSuccess() {
        mPresenter.getPictures();
        mTestScheduler.triggerActions();
        verify(mMockedMainView).setPictures(ArgumentMatchers.anyList());
        verifyNoMoreInteractions(mMockedMainView);
    }

    @After
    public void tearDown(){
        mPresenter.detachView();
    }
}
