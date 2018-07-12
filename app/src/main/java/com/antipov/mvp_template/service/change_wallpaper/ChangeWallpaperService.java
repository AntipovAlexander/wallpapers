package com.antipov.mvp_template.service.change_wallpaper;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.utils.GlideApp;
import com.antipov.mvp_template.utils.SharedPrefs;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;
import com.antipov.mvp_template.utils.shared.CurrentWallpaperPrefs;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import static com.antipov.mvp_template.common.Const.PORTRAIT;


public class ChangeWallpaperService extends JobService implements  IOnWallPaperChanged {

    @Inject WallPaperSetter wallPaperSetter;
    @Inject SharedPrefs sharedPrefs;
    @Inject CurrentWallpaperPrefs currentWallpaperPrefs;
    private Picture mPicture;


    @Override
    public boolean onStartJob(JobParameters params) {
        ((Application) getApplication())
                .getComponent()
                .inject(this);

        boolean isRandom = sharedPrefs.isUseRandomTag();
        boolean isCustom = sharedPrefs.isUseCustomTag();

        String keyword = sharedPrefs.getKeywordForWallpapers();
        Set<String> tagsSet = sharedPrefs.getWallpaperTags();
        String[] tags = tagsSet.toArray(new String[tagsSet.size()]);

        SchedulerProvider schedulerProvider = new AppSchedulerProvider();

        if (isRandom) {
            // if user wants random wallpapers doesn't providing keyword
            keyword = null;
        }

        if (!isCustom && !isRandom){
            //if not custom tag or not random wallpaper selecting one tag from provided array
            int keywordIndex = ThreadLocalRandom.current().nextInt(
                    0,
                    tags.length);
            keyword = tags[keywordIndex];
        }

        // in other cases using provided custom keyword

        RetrofitUtils
                .getApi()
                .create(Api.class)
                .getPhotos(PORTRAIT, keyword, 1)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    pictures -> {
                        if (pictures.size() >= 1){
                            this.mPicture = pictures.get(0);
                            downloadBitmap(mPicture);
                        }
                    },
                    throwable -> { }
                );

        return false;
    }

    private void downloadBitmap(@NonNull Picture picture) {
        GlideApp.with(this)
                .asBitmap()
                .load(picture.getUrls().getFull())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        setWallpaperFromBitmap(resource);
                        return false;
                    }
                }).submit();
    }

    private void setWallpaperFromBitmap(Bitmap bmp) {
        wallPaperSetter.setWallPaper(bmp, this);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void onWallPaperChangedSuccess() {
        currentWallpaperPrefs.save(
                mPicture.getId(),
                mPicture.getUrls().getSmall(),
                mPicture.getUrls().getFull(),
                mPicture.getUser().getName(),
                mPicture.getUser().getBio(),
                mPicture.getUser().getLocation()
        );
    }

    @Override
    public void onWallPaperChangedFailure(String message) {

    }
}
