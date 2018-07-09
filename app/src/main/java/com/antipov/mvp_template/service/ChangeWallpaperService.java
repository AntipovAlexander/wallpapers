package com.antipov.mvp_template.service;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import com.antipov.mvp_template.BuildConfig;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.application.App;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.utils.GlideApp;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;
import com.antipov.mvp_template.utils.rx.AppSchedulerProvider;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class ChangeWallpaperService extends JobService implements IOnWallPaperChanged {

    private static final String BASE_URL = "https://images.unsplash.com/";
    @Inject
    WallPaperSetter wallPaperSetter;

    @Override
    public boolean onStartJob(JobParameters params) {
        ((Application) getApplication()).getComponent().inject(this);

        GlideApp.with(this)
                .asBitmap()
                .load("https://images.unsplash.com/photo-1511645749805-b7541cda346c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI4MTY5fQ&s=3e26be3f2f063cc4beafeb3a2f8583ad")
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        wallPaperSetter.setWallPaper(resource, ChangeWallpaperService.this);
                        return false;
                    }
                }).submit();

        Log.d("scheduler test", "onStartJob");
        return false;
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
}
