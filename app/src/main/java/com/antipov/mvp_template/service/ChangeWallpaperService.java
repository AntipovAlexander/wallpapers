package com.antipov.mvp_template.service;

import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.antipov.mvp_template.application.App;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter;

import javax.inject.Inject;

public class ChangeWallpaperService extends JobService implements IOnWallPaperChanged {

    @Inject
    WallPaperSetter wallPaperSetter;

    @Override
    public boolean onStartJob(JobParameters params) {
        ((Application) getApplication()).getComponent().inject(this);
        Log.d("scheduler test", "onStartJob");
        return true;
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
