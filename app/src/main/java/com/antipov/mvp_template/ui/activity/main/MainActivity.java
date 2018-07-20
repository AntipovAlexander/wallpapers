package com.antipov.mvp_template.ui.activity.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.scheduler.SchedulerActivity;
import com.antipov.mvp_template.ui.adapter.PhotoListAdapter;
import com.antipov.mvp_template.ui.base.BaseActivity;
import com.antipov.mvp_template.utils.GlideApp;
import com.antipov.mvp_template.utils.job.JobScheduledChecker;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    MainPresenter<MainView, MainInteractor> mPresenter;
    @BindView(R.id.rv_photos)
    RecyclerView mPhotos;
    @BindView(R.id.fl_progress)
    FrameLayout mProgress;
    @BindView(R.id.error_layout)
    RelativeLayout mError;
    @BindView(R.id.tv_schedule)
    TextView mSchedule;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefresh;

    private PhotoListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // setup new theme after showing splash
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setupAdapter();
        mPresenter.attachView(this);
        mPresenter.getPictures();
        checkJobIsScheduled();
    }

    private void setupAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mPhotos.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoListAdapter(this, GlideApp.with(this));
        mPhotos.setLayoutManager(mLayoutManager);
        mPhotos.setAdapter(mAdapter);
    }

    @Override
    public void getExtras() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void showLoadingFullScreen() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingFullScreen() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void initListeners() {
        mSchedule.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_schedule:
                Intent i = new Intent(this, SchedulerActivity.class);
                i.putExtra(Const.Args.IS_SCHEDULED, JobScheduledChecker.isWallpapperWorkScheduled(this));
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getPictures();
        checkJobIsScheduled();
    }

    @Override
    public void stopRefreshing() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void checkJobIsScheduled() {
        mPresenter.onJobScheduled(
                JobScheduledChecker.isWallpapperWorkScheduled(this)
        );
    }

    @Override
    public void setIsScheduled() {
        mSchedule.setText(R.string.wallpaper_scheduled);
    }

    @Override
    public void setIsNotScheduled() {
        mSchedule.setText(R.string.schedule_your_wallpaper);
    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void setPictures(List<Picture> model) {
        mAdapter.setPictures(model);
    }

    @Override
    public void showFullScreenError(String error) {
        mError.setVisibility(View.VISIBLE);
        ((TextView) mError.findViewById(R.id.tv_error_text)).setText(error);
    }
}
