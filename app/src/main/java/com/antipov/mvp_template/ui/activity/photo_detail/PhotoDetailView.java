package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.base.IBaseView;

import java.util.List;

public interface PhotoDetailView extends IBaseView {
    void renderLayout(Picture model);

    void showFullScreenError(String error);
}
