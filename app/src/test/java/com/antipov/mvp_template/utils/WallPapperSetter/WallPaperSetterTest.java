package com.antipov.mvp_template.utils.WallPapperSetter;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatDelegate;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.rx.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.Field;

import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class WallPaperSetterTest {

    @Mock
    WallpaperManager mMockWallpaperManager;

    @Mock
    IOnWallPaperChanged mMockListener;

    @Mock
    Bitmap bitmap;

    @Mock
    Context context;

    WallPaperSetter mWallpaperSetter;

    @Before
    public void setUp(){
        mWallpaperSetter = new WallPaperSetter(context);
    }

    @Test
    public void testStartServiceAndUnbind() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Picture mPicture = Picture.getForTests();
        mWallpaperSetter.setWallPaper(bitmap, mPicture, mMockListener);
        verify(context).startService(ArgumentMatchers.any(Intent.class));

        // hack with reflection for changing isBound from 'false' to 'true'
        Field delegateField = Class.forName("com.antipov.mvp_template.utils.WallPapperSetter.WallPaperSetter")
                .getDeclaredField("isBound");
        delegateField.setAccessible(true);
        delegateField.setBoolean(mWallpaperSetter, true);

        mWallpaperSetter.onDestroy();
        verify(context).unbindService(ArgumentMatchers.any(ServiceConnection.class));
        verifyNoMoreInteractions(mMockListener);
    }
}