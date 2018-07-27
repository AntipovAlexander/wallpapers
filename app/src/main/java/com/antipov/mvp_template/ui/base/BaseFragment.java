package com.antipov.mvp_template.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.di.component.ActivityComponent;
import com.antipov.mvp_template.di.component.AppComponent;

public abstract class BaseFragment extends Fragment implements IBaseView {
    private BaseActivity mActivity;
    private MaterialDialog materialDialog;
    private Context context;
    private View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        }

        getExtras();
        initViews();
        initListeners();
        return mView;
    }

    @Nullable
    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(context)
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
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Nullable
    public ActivityComponent getAppComponent() {
        if (mActivity != null) {
            return mActivity.getComponent();
        }
        return null;
    }

    public abstract int getLayoutId();

    public abstract void getExtras();

    public abstract void initViews();

    public abstract void initListeners();
}
