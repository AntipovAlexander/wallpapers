package com.antipov.mvp_template.application;

import android.app.Application;

import com.antipov.mvp_template.di.component.ProductionComponent;
import com.antipov.mvp_template.di.component.DaggerProductionComponent;
import com.antipov.mvp_template.di.module.ActivityModule;

public class App extends Application implements com.antipov.mvp_template.application.Application {

    public ProductionComponent component;

    @Override
    public ProductionComponent getComponent() {
        if (component == null){
            component = DaggerProductionComponent.builder().activityModule(new ActivityModule()).build();
        }
        return component;
    }
}
