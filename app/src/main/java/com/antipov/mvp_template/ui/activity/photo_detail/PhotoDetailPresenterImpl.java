package com.antipov.mvp_template.ui.activity.photo_detail;

import android.graphics.Bitmap;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.BasePresenter;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;

import javax.inject.Inject;

public class PhotoDetailPresenterImpl <V extends PhotoDetailView, I extends PhotoDetailInteractor> extends BasePresenter<V, I>
    implements PhotoDetailPresenter<V, I>, IOnWallPaperChanged {

    private final WallPaperSetter mWallpaperSetter;
    private Picture mPicture;

    @Inject
    public PhotoDetailPresenterImpl(I interactor, WallPaperSetter wallPaperSetter) {
        super(interactor);
        this.mWallpaperSetter = wallPaperSetter;
    }

    @Override
    public void getPicture(String id) {
        if (isViewAttached()) getView().showLoadingFullScreen();
        getInteractor().getPicture(id).subscribe(picture -> {
            if (isViewAttached()){
                this.mPicture = picture;
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

    @Override
    public void setWallPaper(Bitmap mBitmap) {
        if (!isViewAttached()) return;
        getView().showLoading();
        mWallpaperSetter.setWallPaper(mBitmap, this);
    }

    @Override
    public void onWallPaperChangedSuccess() {
        if (!isViewAttached()) return;
        getInteractor().saveCurrentWallpaper(
                mPicture.getId(),
                mPicture.getUrls().getSmall(),
                mPicture.getUrls().getFull(),
                mPicture.getUser().getName(),
                mPicture.getUser().getBio(),
                mPicture.getUser().getLocation()
        );
        getView().hideLoading();
        getView().showMessage(R.string.setup_successfully);
    }

    @Override
    public void onWallPaperChangedFailure(String message) {
        if (!isViewAttached()) return;
        getView().hideLoading();
    }
}
