package com.antipov.mvp_template.di.component;

import com.antipov.mvp_template.di.module.ActivityModule;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailInteractor;

import dagger.Component;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(PhotoDetailActivity photoDetailActivity);
}
