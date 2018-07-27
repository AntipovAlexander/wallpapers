package com.antipov.mvp_template.di.module;

import android.app.Application;
import android.content.Context;

import com.antipov.mvp_template.di.ApplicationContext;
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
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentInteractor;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentInteractorImpl;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentPresenter;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentPresenterImpl;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragmentView;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;
import com.antipov.mvp_template.utils.prefs.CurrentWallpaperPrefs;
import com.antipov.mvp_template.utils.prefs.SharedPrefs;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Module
public class AppModule {

    private final Application mApplication;
    private final AppSchedulerProvider mScheduleProvider;

    public AppModule(Application application, AppSchedulerProvider appSchedulerProvider) {
        this.mScheduleProvider = appSchedulerProvider;
        this.mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    public SharedPrefs provideSharedPrefs() {
        return new SharedPrefs(mApplication);
    }

    @Provides
    public CurrentWallpaperPrefs provideCurrentWallpaperPrefs() {
        return new CurrentWallpaperPrefs(mApplication);
    }

    @Provides
    public WallPaperSetter provideWallPaperSetter() {
        return new WallPaperSetter(mApplication);
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return this.mScheduleProvider;
    }
}
