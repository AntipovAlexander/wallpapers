package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.ui.activity.base.IBasePresenter;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface MainPresenter<V extends MainView, I extends MainInteractor> extends IBasePresenter<V, I> {
    void getPictures();
}
