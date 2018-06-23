package com.antipov.mvp_template;

import android.app.Application;
import com.antipov.mvp_template.di.component.AppComponent;

public class MockApp extends Application implements com.antipov.mvp_template.application.Application {
    private AppComponent component;

    @Override
    public AppComponent getComponent() {
        return component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }
}
