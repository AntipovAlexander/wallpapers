package com.antipov.mvp_template.ui.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.antipov.mvp_template.Application;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.di.component.ActivityComponent;
import com.antipov.mvp_template.di.component.DaggerActivityComponent;
import com.antipov.mvp_template.di.module.ActivityModule;
import com.antipov.mvp_template.utils.DialogUtils;
import com.antipov.mvp_template.utils.NetworkUtils;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // some activities may be without content, e.g splash screen.
        // get layout id may return -1 in that case
        if (getLayoutId() != -1){
            setContentView(getLayoutId());
        }
        getExtras();
        initViews();
        initToolbar();
        initListeners();
    }

    public ActivityComponent getComponent(){
        return ((Application)getApplication()).getComponent();
    }

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_title)
                .content(R.string.please_wait)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {
        showMessage(resId);
    }

    @Override
    public void onError(String message) {
        showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showSnackbar(this, message);
    }

    @Override
    public void showMessage(int resId) {
        DialogUtils.showSnackbar(this, getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void showLoadingFullScreen() {

    }

    @Override
    public void hideLoadingFullScreen() {

    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
