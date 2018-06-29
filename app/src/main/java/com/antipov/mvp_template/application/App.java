package com.antipov.mvp_template.application;

import android.app.Application;

import com.antipov.mvp_template.di.component.AppComponent;
import com.antipov.mvp_template.di.component.DaggerAppComponent;
import com.antipov.mvp_template.di.module.AppModule;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;

public class App extends Application implements com.antipov.mvp_template.application.Application {

    public AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public AppComponent getComponent() {
        if (component == null){
            component = DaggerAppComponent.builder().appModule(new AppModule(
                    getApplicationContext(),
                    new AppSchedulerProvider())).build();
        }
        return component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }
}
