package com.antipov.mvp_template.ui.base;

import javax.inject.Inject;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class BasePresenter<V extends IBaseView, I extends IBaseInteractor> implements IBasePresenter<V, I> {

    V mView;

    I mInteractor;

    @Inject
    public BasePresenter(I interactor) {
        this.mInteractor = interactor;
    }

    @Override
    public void attachView(V mvpView) {
        this.mView = mvpView;
    }

    @Override
    public V getView() {
        return mView;
    }

    @Override
    public I getInteractor() {
        return mInteractor;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
