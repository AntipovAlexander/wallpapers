package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.ui.activity.base.IBasePresenter;

public interface PhotoDetailPresenter <V extends PhotoDetailView, I extends PhotoDetailInteractor> extends IBasePresenter<V, I>{
    void getPicture(String id);
}
