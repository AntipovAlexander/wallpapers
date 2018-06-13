package com.antipov.mvp_template.ui.activity.photo_detail;

import android.os.Bundle;
import android.widget.ImageView;

import com.antipov.mvp_template.Const;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.base.BaseActivity;
import com.antipov.mvp_template.utils.GlideApp;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailView {

    @BindView(R.id.iv_image) ImageView mImage;
    @Inject PhotoDetailPresenter<PhotoDetailView, PhotoDetailInteractor> mPresenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
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
    public void initToolbar() {

    }

    @Override
    public void renderLayout(Picture model) {
        GlideApp.with(this)
                .load(model.getUrls().getFull())
                .into(mImage);
    }

    @Override
    public void showFullScreenError(String error) {

    }
}
