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
        if (isViewAttached()) getView().showLoading();
        getInteractor().getPicture(id).subscribe(picture -> {
            if (isViewAttached()){
                getView().hideLoading();
                getView().renderLayout(picture);
            }},
                throwable -> {
            if (isViewAttached()){
                getView().hideLoading();
                getView().showFullScreenError(throwable.getMessage());
            }});
    }
}
