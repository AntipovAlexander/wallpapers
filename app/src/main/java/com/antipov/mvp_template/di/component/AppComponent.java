package com.antipov.mvp_template.di.component;

import com.antipov.mvp_template.di.module.AppModule;
import com.antipov.mvp_template.service.job.change_wallpaper.ChangeWallpaperJob;
import com.antipov.mvp_template.service.foreground.change_wallpaper.ChangeWallPaperForeground;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragment;

import dagger.Component;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(PhotoDetailActivity photoDetailActivity);

    void inject(SchedulerFragment schedulerFragment);

    void inject(ChangeWallpaperJob changeWallpaperService);

    void inject(ChangeWallPaperForeground changeWallPaperForeground);
}

