package com.antipov.mvp_template.di.module;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.antipov.mvp_template.di.ActivityContext;
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
import com.antipov.mvp_template.ui.dialog.WallpaperTargetDialog;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentInteractor;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentInteractorImpl;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentPresenter;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentPresenterImpl;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentView;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    Activity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideActivityContext(){
        return activity;
    }

    @Provides
    WallpaperTargetDialog provideWallpaperTargetDialog(){
        return new WallpaperTargetDialog(activity);
    }

    @Provides
    public MainPresenter<MainView, MainInteractor> provideMainPresenter(MainPresenterImpl<MainView, MainInteractor> presenter) {
        return presenter;
    }

    @Provides
    public PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> providerPhotoDetailPresenter(
            PhotoDetailPresenterImpl<PhotoDetailView, PhotoDetailInteractor> presenter
    ) {
        return presenter;
    }

    @Provides
    public SchedulerFragmentPresenter<SchedulerFragmentView, SchedulerFragmentInteractor> providerSchedulerFragmentPresenter(
            SchedulerFragmentPresenterImpl<SchedulerFragmentView, SchedulerFragmentInteractor> presenter
    ) {
        return presenter;
    }

    @Provides
    public MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    public PhotoDetailInteractor providePhotoDetailInteractor(PhotoDetailInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    public SchedulerFragmentInteractor provideSchedulerFragmentInteractor(SchedulerFragmentInteractorImpl interactor) {
        return interactor;
    }
}
