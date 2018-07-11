package com.antipov.mvp_template.utils.WallPapperSetter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.antipov.mvp_template.rx.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

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

    WallPaperSetter mWallpaperSetter;
    TestScheduler mTestScheduler;

    @Before
    public void setUp(){
        mTestScheduler = new TestScheduler();
        mWallpaperSetter = new WallPaperSetter(new TestSchedulerProvider(mTestScheduler), mMockWallpaperManager);
    }

    @Test
    public void setWallPaperSuccess() {
        mWallpaperSetter.setWallPaper(bitmap, mMockListener);
        mTestScheduler.triggerActions();
        verify(mMockListener).onWallPaperChangedSuccess();
        verifyNoMoreInteractions(mMockListener);
    }

    @Test
    public void setWallPaperError() throws IOException {
        doThrow(new IOException()).when(mMockWallpaperManager).setBitmap(ArgumentMatchers.any(Bitmap.class));
        mWallpaperSetter.setWallPaper(bitmap, mMockListener);
        mTestScheduler.triggerActions();
        verify(mMockListener).onWallPaperChangedFailure(throwable.getMessage());
        verifyNoMoreInteractions(mMockListener);
    }
}