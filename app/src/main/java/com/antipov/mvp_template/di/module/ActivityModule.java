package com.antipov.mvp_template.di.module;

import com.antipov.mvp_template.ui.activity.main.MainInteractor;
import com.antipov.mvp_template.ui.activity.main.MainInteractorImpl;
import com.antipov.mvp_template.ui.activity.main.MainPresenter;
import com.antipov.mvp_template.ui.activity.main.MainPresenterImpl;
import com.antipov.mvp_template.ui.activity.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Module
public class ActivityModule {

    @Provides
    MainPresenter<MainView, MainInteractor> provideMainPresenter(MainPresenterImpl<MainView, MainInteractor> presenter){
        return presenter;
    }

    @Provides
    MainInteractor provideMainInteractor(MainInteractorImpl interactor){
        return interactor;
    }
}
