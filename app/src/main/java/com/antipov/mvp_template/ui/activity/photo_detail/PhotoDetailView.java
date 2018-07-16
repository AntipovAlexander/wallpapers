package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.IBaseView;

public interface PhotoDetailView extends IBaseView {
    void renderLayout(Picture model);

    void showFullScreenError(String error);

    void startBrowserIntent();
}
