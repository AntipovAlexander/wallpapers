package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.prefs.CurrentWallpaperPrefs;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class PhotoDetailInteractorImpl extends BaseInteractor implements PhotoDetailInteractor {

    private final CurrentWallpaperPrefs mPrefs;

    @Inject
    public PhotoDetailInteractorImpl(SchedulerProvider scheduler, CurrentWallpaperPrefs prefs) {
        super(scheduler);
        this.mPrefs = prefs;
    }

    @Override
    public void saveCurrentWallpaper(String id, String small, String full, String html,  String name, String bio, String location) {
        mPrefs.save(id, small, full, html, name, bio, location);
    }
}
