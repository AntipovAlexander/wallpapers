package com.antipov.mvp_template.di.module;

import android.util.Log;

import com.antipov.mvp_template.ui.activity.main.MainInteractor;
import com.antipov.mvp_template.ui.activity.main.MainInteractorImpl;
import com.antipov.mvp_template.ui.activity.main.MainPresenter;
import com.antipov.mvp_template.ui.activity.main.MainPresenterImpl;
import com.antipov.mvp_template.ui.activity.main.MainView;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailInteractor;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailInteractorImpl;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailPresenter;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailPresenterImpl;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailView;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Module
public class TestActivityModule {

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    MainPresenter<MainView, MainInteractor> provideMainPresenter(MainPresenterImpl<MainView, MainInteractor> presenter){
        return presenter;
    }

    @Provides
    PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> providerPhotoDetailPresenter(
            PhotoDetailPresenterImpl<PhotoDetailView, PhotoDetailInteractor> presenter
    ){
        return presenter;
    }

    @Provides
    MainInteractor provideMainInteractor(MainInteractorImpl interactor){
        return interactor;
    }

    @Provides
    PhotoDetailInteractor providePhotoDetailInteractor(PhotoDetailInteractorImpl interactor){
        return interactor;
    }
}
