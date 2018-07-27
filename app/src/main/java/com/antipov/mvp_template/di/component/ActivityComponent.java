package com.antipov.mvp_template.di.component;

import com.antipov.mvp_template.di.module.ActivityModule;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(PhotoDetailActivity photoDetailActivity);

    void inject(SchedulerFragment schedulerFragment);
}
