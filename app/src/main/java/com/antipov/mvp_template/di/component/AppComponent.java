package com.antipov.mvp_template.di.component;

import android.content.Context;

import com.antipov.mvp_template.di.ApplicationContext;
import com.antipov.mvp_template.di.module.AppModule;
import com.antipov.mvp_template.service.foreground.change_wallpaper.ChangeWallPaperForeground;
import com.antipov.mvp_template.service.job.change_wallpaper.ChangeWallpaperJob;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragment;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;
import com.antipov.mvp_template.utils.prefs.CurrentWallpaperPrefs;
import com.antipov.mvp_template.utils.prefs.SharedPrefs;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import dagger.Component;
import dagger.Provides;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ChangeWallpaperJob changeWallpaperService);

    void inject(ChangeWallPaperForeground changeWallPaperForeground);

    // provide dependencies from app module to dependent components

    @ApplicationContext
    Context context();

    SharedPrefs sharedPrefs();

    CurrentWallpaperPrefs currentWallpaperPrefs();

    WallPaperSetter wallpaperSetter();

    SchedulerProvider schedulerProvider();

}

