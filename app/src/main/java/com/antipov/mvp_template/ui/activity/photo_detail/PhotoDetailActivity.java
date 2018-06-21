package com.antipov.mvp_template.ui.activity.photo_detail;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antipov.mvp_template.App;
import com.antipov.mvp_template.Const;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.base.BaseActivity;
import com.antipov.mvp_template.utils.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailView {

    @Inject PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> mPresenter;
    @BindView(R.id.iv_image) ImageView mImage;
    @BindView(R.id.fl_progress) FrameLayout mProgress;
    @BindView(R.id.tv_name) TextView mName;
    @BindView(R.id.tv_location) TextView mLocation;
    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.bottom_sheet) LinearLayout mBottomSheet;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getPicture(id);
    }

    @Override
    public void getExtras() {
        Bundle args = getIntent().getExtras();
        if (args != null){
            id = args.getString(Const.Args.ID);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo_detail, menu);
        return true;
    }

    @Override
    public void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.transluent_toolbar)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void renderLayout(Picture model) {
        GlideApp.with(this)
                .load(model.getUrls().getFull())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mPresenter.onPictureNotLoaded(e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mPresenter.onPictureLoaded();
                        return false;
                    }
                })
                .into(mImage);

        mName.setText(model.getUser().getName());

        if (model.getUser().getLocation() == null || model.getUser().getLocation().isEmpty()) mLocation.setVisibility(View.GONE);
            else mLocation.setText(model.getUser().getLocation());

        if (model.getUser().getBio() == null || model.getUser().getBio().isEmpty()) mDescription.setVisibility(View.GONE); else
            mDescription.setText(model.getUser().getBio());

        mBottomSheet.setVisibility(View.VISIBLE);
    }



    @Override
    public void showFullScreenError(String error) {

    }
}
