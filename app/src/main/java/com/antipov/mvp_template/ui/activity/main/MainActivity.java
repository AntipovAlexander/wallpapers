package com.antipov.mvp_template.ui.activity.main;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.ui.activity.base.BaseActivity;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.adapter.PhotoListAdapter;
import com.antipov.mvp_template.utils.GlideApp;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter<MainView, MainInteractor> mPresenter;
    @BindView(R.id.rv_photos) RecyclerView mPhotos;
    private PhotoListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getPictures();
        setupAdapter();
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
        ButterKnife.bind(this);
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
    public void initListeners() {

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

    }
}
