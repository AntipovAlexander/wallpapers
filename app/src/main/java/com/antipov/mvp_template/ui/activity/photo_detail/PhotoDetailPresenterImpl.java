package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.ui.activity.base.BasePresenter;

import javax.inject.Inject;

public class PhotoDetailPresenterImpl <V extends PhotoDetailView, I extends PhotoDetailInteractor> extends BasePresenter<V, I>
    implements PhotoDetailPresenter<V, I>{

    @Inject
    public PhotoDetailPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void getPicture(String id) {
        if (isViewAttached()) getView().showLoadingFullScreen();
        getInteractor().getPicture(id).subscribe(picture -> {
            if (isViewAttached()){
                getView().renderLayout(picture);
            }},
                throwable -> {
            if (isViewAttached()){
                getView().hideLoadingFullScreen();
                getView().showFullScreenError(throwable.getMessage());
            }});
    }

    @Override
    public void onPictureNotLoaded(String message) {
        if (!isViewAttached()) return;
        getView().hideLoadingFullScreen();
        getView().showFullScreenError(message);
    }

    @Override
    public void onPictureLoaded() {
        if (!isViewAttached()) return;
        getView().hideLoadingFullScreen();
    }
}
