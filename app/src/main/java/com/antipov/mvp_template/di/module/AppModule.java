package com.antipov.mvp_template.di.module;

import android.content.Context;

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

    private final Context mContext;
    private final AppSchedulerProvider mScheduleProvider;

    public AppModule(Context context, AppSchedulerProvider appSchedulerProvider) {
        this.mContext = context;
        this.mScheduleProvider = appSchedulerProvider;
    }

    @Provides
    public SharedPrefs provideSharedPrefs() {
        return new SharedPrefs(mContext);
    }

    @Provides
    public CurrentWallpaperPrefs provideCurrentWallpaperPrefs() {
        return new CurrentWallpaperPrefs(mContext);
    }

    @Provides
    public WallPaperSetter provideWallPaperSetter() {
        return new WallPaperSetter(mContext);
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return this.mScheduleProvider;
    }
}
