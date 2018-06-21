package com.antipov.mvp_template.di.component;

import com.antipov.mvp_template.di.module.TestActivityModule;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;

import dagger.Component;

@Component(modules = {TestActivityModule.class})
public interface TestComponent extends ProductionComponent {
    void inject(MainActivity mainActivity);

    void inject(PhotoDetailActivity photoDetailActivity);
}
