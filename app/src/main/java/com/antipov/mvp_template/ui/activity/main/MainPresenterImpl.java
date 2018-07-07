package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class MainPresenterImpl<V extends MainView, I extends MainInteractor> extends BasePresenter<V, I>
        implements MainPresenter<V, I> {

    @Inject
    public MainPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void getPictures() {
        if (isViewAttached()) getView().showLoadingFullScreen();
        getInteractor().getPictures().subscribe(
                pictures -> { if (isViewAttached()){
                    getView().hideLoadingFullScreen();
                    getView().setPictures(pictures);
                }},
                throwable -> { if (isViewAttached()){
                    getView().hideLoadingFullScreen();
                    getView().showFullScreenError(throwable.getLocalizedMessage());
                }});
    }
}
