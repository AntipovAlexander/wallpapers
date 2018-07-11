package com.antipov.mvp_template.utils.WallPapperSetter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;

public class WallPaperSetter {
    private final SchedulerProvider provider;
    private final WallpaperManager manager;

    @Inject
    public WallPaperSetter(SchedulerProvider provider, WallpaperManager wallpaperManager) {
        this.provider = provider;
        this.manager = wallpaperManager;
    }

    /**
     * Async method for setup wallpaper
     * Using rx java for providing test schedulers in unt tests
     *
     * https://medium.com/code-yoga/using-rxjava-instead-asynctask-3052697c0f48
     *
     * @param bitmap image which will be set up as wallpaper
     * @param onWallPaperChanged set up wallpaper listener
     */
    public void setWallPaper(Bitmap bitmap, IOnWallPaperChanged onWallPaperChanged){
        Observable.fromCallable(() -> {
            synchronized (WallPaperSetter.class){
                try {
                    manager.setBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        })
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(isSuccess -> {
                    if (isSuccess){
                        onWallPaperChanged.onWallPaperChangedSuccess();
                    } else {
                        onWallPaperChanged.onWallPaperChangedFailure("Error while setting wallpaper");
                    }
                },
                        throwable -> onWallPaperChanged.onWallPaperChangedFailure(throwable.getMessage()));
    }
}
