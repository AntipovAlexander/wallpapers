package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;
import com.antipov.mvp_template.utils.shared.CurrentWallpaperPrefs;

import javax.inject.Inject;

import rx.Observable;

public class PhotoDetailInteractorImpl extends BaseInteractor implements PhotoDetailInteractor {

    private final CurrentWallpaperPrefs mPrefs;

    @Inject
    public PhotoDetailInteractorImpl(SchedulerProvider scheduler, CurrentWallpaperPrefs prefs) {
        super(scheduler);
        this.mPrefs = prefs;
    }

    @Override
    public Observable<Picture> getPicture(String id) {
        Observable<Picture> call = RetrofitUtils.getApi().
                create(Api.class).getPicture(id);
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }

    @Override
    public void saveCurrentWallpaper(String id, String small, String full, String name, String bio, String location) {
        mPrefs.save(id, small, full, name, bio, location);
    }
}
