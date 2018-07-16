package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.ui.base.IBaseInteractor;

public interface PhotoDetailInteractor extends IBaseInteractor {

    void saveCurrentWallpaper(String id, String small, String full, String name, String bio, String location);
}
