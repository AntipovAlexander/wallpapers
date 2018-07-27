package com.antipov.mvp_template.ui.dialog;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.utils.DialogUtils;

public class WallpaperTargetDialog {
    private MaterialDialog dialog;
    private View mHomeScreen;
    private View mLockScreen;
    private View mLockHomeScreen;
    private OnWallpaperTargetSelected listener;

    public WallpaperTargetDialog(Context context) {
        dialog = DialogUtils.createCustomDialog(
                context,
                context.getString(R.string.dialog_title_wallpaper_target),
                R.layout.dialog_wallpaper_target,
                false,
                null,
                context.getString(R.string.dismiss),
                null
        ).build();

        initDialogListeners();
    }

    private void initDialogListeners() {
        mHomeScreen = dialog.findViewById(R.id.tv_home_screen);
        mLockScreen = dialog.findViewById(R.id.tv_lock_screen);
        mLockHomeScreen = dialog.findViewById(R.id.tv_home_and_lock_screen);

        mHomeScreen.setOnClickListener(v -> {
            listener.onSelectedHomeScreenAsTarget();
            dialog.dismiss();
        });
        mLockScreen.setOnClickListener(v -> {
            listener.onSelectedLockScreenAsTarget();
            dialog.dismiss();
        });
        mLockHomeScreen.setOnClickListener(v -> {
            listener.onSelectedHomeAndLockScreenAsTarget();
            dialog.dismiss();
        });
    }

    public void show(OnWallpaperTargetSelected listener) {
        this.listener = listener;
        dialog.show();
    }

    public interface OnWallpaperTargetSelected {
        void onSelectedHomeScreenAsTarget();

        void onSelectedLockScreenAsTarget();

        void onSelectedHomeAndLockScreenAsTarget();
    }
}
