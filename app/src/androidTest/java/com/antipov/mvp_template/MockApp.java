package com.antipov.mvp_template;

import android.app.Application;
import com.antipov.mvp_template.di.component.ActivityComponent;
import com.antipov.mvp_template.di.component.DaggerTestActivityComponent;
import com.antipov.mvp_template.di.module.TestActivityModule;

public class MockApp extends Application implements com.antipov.mvp_template.Application {
    private ActivityComponent component;

    @Override
    public ActivityComponent getComponent() {
        if (component == null){
            component = DaggerTestActivityComponent.builder().testActivityModule(new TestActivityModule()).build();
        }
        return component;
    }
}
