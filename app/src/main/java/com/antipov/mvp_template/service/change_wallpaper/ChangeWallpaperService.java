package com.antipov.mvp_template.service.change_wallpaper;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.utils.GlideApp;
import com.antipov.mvp_template.utils.SharedPrefs;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

import static com.antipov.mvp_template.common.Const.PORTRAIT;
import static com.antipov.mvp_template.common.Const.WALLPAPER;


public class ChangeWallpaperService extends JobService implements  IOnWallPaperChanged {

    @Inject WallPaperSetter wallPaperSetter;
    @Inject SharedPrefs sharedPrefs;


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
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    pictures -> {
                        if (pictures.size() >= 1){
                            downloadBitmap(pictures.get(0));
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

    }

    @Override
    public void onWallPaperChangedFailure() {

    }

    public boolean intToBool(int i) {
        return i == 1;
    }
}
