package com.antipov.mvp_template.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showLoadingFullScreen();

    void hideLoadingFullScreen();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
