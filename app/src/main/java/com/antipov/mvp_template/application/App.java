package com.antipov.mvp_template.application;

import android.app.Application;
import android.preference.PreferenceManager;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.di.component.AppComponent;
import com.antipov.mvp_template.di.component.DaggerAppComponent;
import com.antipov.mvp_template.di.module.AppModule;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;

public class App extends Application implements com.antipov.mvp_template.application.Application {

    public AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        //set up default values for shared preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public AppComponent getComponent() {
        if (component == null) {
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
