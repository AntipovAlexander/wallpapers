package com.antipov.mvp_template;

import android.app.Application;

import com.antipov.mvp_template.di.component.DaggerTestComponent;
import com.antipov.mvp_template.di.component.ProductionComponent;
import com.antipov.mvp_template.di.module.TestActivityModule;


public class MockApp extends Application implements com.antipov.mvp_template.application.Application {
    private ProductionComponent component;

    @Override
    public ProductionComponent getComponent() {
        return component;
    }

    public void setComponent(ProductionComponent component) {
        this.component = component;
    }
}
