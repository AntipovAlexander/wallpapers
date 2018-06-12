package com.antipov.mvp_template.main_test;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.main.MainInteractor;
import com.antipov.mvp_template.ui.activity.main.MainPresenter;
import com.antipov.mvp_template.ui.activity.main.MainPresenterImpl;
import com.antipov.mvp_template.ui.activity.main.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by AlexanderAntipov on 06.06.2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock MainView mMockedMainView;
    @Mock MainInteractor mMockedInteractor;
    @Mock MainInteractor.OnPhotosGet listener;
    MainPresenter<MainView, MainInteractor> mPresenter;

    @Before
    public void setUp(){
        mPresenter = new MainPresenterImpl<>(mMockedInteractor);
        mPresenter.attachView(mMockedMainView);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPictures(){
//        mMockedInteractor.getPictures(listener);
//        when(mMockedInteractor.getPictures(listener)).then(m)
//        verify(mMockedMainView).setPictures(pictures);
    }

    @Before
    public void tearDown(){
        mPresenter.detachView();
    }
}
