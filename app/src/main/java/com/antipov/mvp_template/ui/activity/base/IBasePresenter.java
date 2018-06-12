package com.antipov.mvp_template.ui.activity.base;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface IBasePresenter<V extends IBaseView, I extends IBaseInteractor> {

    void attachView(V mvpView);

    V getView();

    I getInteractor();

    boolean isViewAttached();

    void detachView();
}
