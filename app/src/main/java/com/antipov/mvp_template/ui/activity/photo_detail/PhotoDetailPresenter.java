package com.antipov.mvp_template.ui.activity.photo_detail;

import android.graphics.Bitmap;

import com.antipov.mvp_template.ui.activity.base.IBasePresenter;

public interface PhotoDetailPresenter <V extends PhotoDetailView, I extends PhotoDetailInteractor> extends IBasePresenter<V, I>{
    void getPicture(String id);

    void onPictureNotLoaded(String message);

    void onPictureLoaded();

    void setWallPaper(Bitmap mBitmap);
}
