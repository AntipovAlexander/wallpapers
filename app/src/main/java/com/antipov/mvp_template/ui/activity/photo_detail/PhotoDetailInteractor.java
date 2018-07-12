package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.IBaseInteractor;

import rx.Observable;

public interface PhotoDetailInteractor extends IBaseInteractor {
    Observable<Picture> getPicture(String id);

    void saveCurrentWallpaper(String id, String small, String full, String name, String bio, String location);
}
