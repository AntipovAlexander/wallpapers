package com.antipov.mvp_template;

import android.app.Application;

import com.antipov.mvp_template.di.component.ActivityComponent;
import com.antipov.mvp_template.di.component.DaggerActivityComponent;
import com.antipov.mvp_template.di.module.ActivityModule;

public class App extends Application implements com.antipov.mvp_template.Application {

    public ActivityComponent component;

    @Override
    public ActivityComponent getComponent() {
        if (component == null){
            component = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
        }
        return component;
    }
}
