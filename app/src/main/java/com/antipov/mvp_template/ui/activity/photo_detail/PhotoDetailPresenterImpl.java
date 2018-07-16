package com.antipov.mvp_template.ui.activity.photo_detail;

import android.graphics.Bitmap;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.BasePresenter;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;

import javax.inject.Inject;

public class PhotoDetailPresenterImpl<V extends PhotoDetailView, I extends PhotoDetailInteractor> extends BasePresenter<V, I>
        implements PhotoDetailPresenter<V, I>, IOnWallPaperChanged {

    private final WallPaperSetter mWallpaperSetter;

    @Inject
    public PhotoDetailPresenterImpl(I interactor, WallPaperSetter wallPaperSetter) {
        super(interactor);
        this.mWallpaperSetter = wallPaperSetter;
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
    public void setWallPaper(Bitmap mBitmap, Picture mPicture) {
        if (!isViewAttached()) return;
        getView().showLoading();
        mWallpaperSetter.setWallPaper(mBitmap, mPicture, this);
    }

    @Override
    public void onViewPrepared(Picture mPicture) {
        if (!isViewAttached()) return;
        getView().renderLayout(mPicture);
    }

    @Override
    public void onWallPaperChangedSuccess(Picture mPicture) {
        if (!isViewAttached()) return;
        getInteractor().saveCurrentWallpaper(
                mPicture.getId(),
                mPicture.getUrls().getSmall(),
                mPicture.getUrls().getRegular(),
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

    @Override
    public void detachView() {
        super.detachView();
        mWallpaperSetter.onDestroy();
    }
}
