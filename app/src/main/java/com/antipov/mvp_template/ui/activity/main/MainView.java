package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.IBaseView;

import java.util.List;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface MainView extends IBaseView {
    void setPictures(List<Picture> model);

    void showFullScreenError(String error);

    void stopRefreshing();

    void checkJobIsScheduled();

    void setIsScheduled();

    void setIsNotScheduled();
}
