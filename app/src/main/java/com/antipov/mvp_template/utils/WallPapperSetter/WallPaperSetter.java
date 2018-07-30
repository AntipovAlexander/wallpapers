package com.antipov.mvp_template.utils.WallPapperSetter;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.service.foreground.change_wallpaper.ChangeWallPaperForeground;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailInteractor;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailPresenterImpl;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailView;

import javax.inject.Inject;

public class WallPaperSetter {
    private final Context context;
    private ChangeWallPaperForeground myService;
    private boolean isBound;
    private ServiceConnection myConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            ChangeWallPaperForeground.ChangeWallPaperForegroundBinder binder = (ChangeWallPaperForeground.ChangeWallPaperForegroundBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }

    };


    @Inject
    public WallPaperSetter(Context context) {
        this.context = context;
        this.context.bindService(new Intent(context, ChangeWallPaperForeground.class), myConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Method for setting up wallpaper via service connection. Has overloaded version
     * with target flag {@link WallPaperSetter#setWallPaper(Bitmap, Picture, int, IOnWallPaperChanged)}
     *
     * @param bitmap             image which will be set up as wallpaper
     * @param picture           wallpaper model
     * @param listener set up wallpaper listener
     */
    public void setWallPaper(Bitmap bitmap, Picture picture, IOnWallPaperChanged listener) {
        startService();

        synchronized (WallPaperSetter.class) {
            if (myService != null){
                myService.setupWallpaper(bitmap, picture, listener);
            }
        }
    }

    /**
     * Method for setting up wallpaper via service connection
     *
     * @param bitmap             image which will be set up as wallpaper
     * @param mPicture           wallpaper model
     * @param flag               target for wallpaper: {@link WallpaperManager#FLAG_LOCK}, {@link WallpaperManager#FLAG_SYSTEM}
     * @param listener set up wallpaper listener
     */
    public void setWallPaper(Bitmap bitmap, Picture mPicture, int flag, IOnWallPaperChanged listener) {
        startService();

        synchronized (WallPaperSetter.class) {
            if (myService != null){
                myService.setupWallpaper(bitmap, mPicture, flag, listener);
            }
        }
    }

    private void startService() {
        Intent i = new Intent(context, ChangeWallPaperForeground.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        } else {
            context.startService(i);
        }
    }

    public void onDestroy() {
        if (isBound) {
            context.unbindService(myConnection);
        }
    }
}
