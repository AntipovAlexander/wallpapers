package com.antipov.mvp_template.service.foreground.change_wallpaper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.utils.WallPapperSetter.IOnWallPaperChanged;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;

public class ChangeWallPaperForeground extends Service {

    private final String CHANNEL_ID = "com.antipov.wallpapers";
    private String CHANNEL_NAME;
    private final int NOTIFICATION_ID = 696;
    private final IBinder mBinder = new ChangeWallPaperForegroundBinder();
    @Inject
    SchedulerProvider mProvider;
    private Notification mNotification;
    private Observable<Boolean> observable;

    public ChangeWallPaperForeground() {
    }

    @Override
    public void onCreate() {
        ((Application) getApplication()).getComponent().inject(this);
        CHANNEL_NAME = getString(R.string.app_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);

            mChannel.setImportance(NotificationManager.IMPORTANCE_LOW);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.createNotificationChannel(mChannel);

            Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.set_up_your_wallpaper))
                    .setSmallIcon(R.drawable.ic_wallpaper)
                    .setContentText(getString(R.string.wait_a_second))
                    .setAutoCancel(true);

            mNotification = builder.build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.set_up_your_wallpaper))
                    .setSmallIcon(R.drawable.ic_wallpaper)
                    .setContentText(getString(R.string.wait_a_second))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            mNotification = builder.build();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, mNotification);
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setupWallpaper(Bitmap bitmap, Picture mPicture, IOnWallPaperChanged listener) {
        observable = Observable.fromCallable(() -> {
            try {
                WallpaperManager.getInstance(this).setBitmap(bitmap);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });

        subscribeForWallpaperChanges(observable, listener, mPicture);
    }

    @RequiresApi(24)
    public void setupWallpaper(Bitmap bitmap, Picture mPicture, int flag, IOnWallPaperChanged listener) {
        observable = Observable.fromCallable(() -> {
            try {
                WallpaperManager.getInstance(this).setBitmap(bitmap, null, true, flag);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });

        subscribeForWallpaperChanges(observable, listener, mPicture);
    }

    private void subscribeForWallpaperChanges(Observable<Boolean> observable, IOnWallPaperChanged listener, Picture mPicture) {
        observable
            .subscribeOn(mProvider.io())
            .observeOn(mProvider.ui())
            .subscribe(
                    isSuccess -> {
                        stopThisService();
                        if (isSuccess) {
                            listener.onWallPaperChangedSuccess(mPicture);
                        } else {
                            listener.onWallPaperChangedFailure("Error while setting wallpaper");
                        }
                    },

                    throwable -> {
                        stopThisService();
                        listener.onWallPaperChangedFailure(throwable.getMessage());
                    });
    }

    private void stopThisService() {
        stopSelf();
        stopForeground(true);
    }

    public class ChangeWallPaperForegroundBinder extends Binder {
        public ChangeWallPaperForeground getService() {
            return ChangeWallPaperForeground.this;
        }
    }
}
