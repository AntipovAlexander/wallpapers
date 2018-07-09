package com.antipov.mvp_template.service.change_wallpaper;

import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ChangeWallpaperInteractorImpl extends BaseInteractor implements ChangeWallpaperInteractor {

    @Inject
    public ChangeWallpaperInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }
}
