package com.antipov.mvp_template.utils.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;

import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.utils.Version;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobUtilsTest {

    @Mock
    Version mockedVersion;

    @Mock
    Context mockedContext;

    @Mock
    JobScheduler mockedJobScheduler;

    @Mock
    JobInfo mockedJobInfo;

    JobUtils jobUtils;

    @Before
    public void setUp() {
        jobUtils = new JobUtils(mockedVersion, mockedContext);
    }

    @Test
    public void isWallpaperWorkScheduledPositive() {
        when(mockedVersion.isApiVersionGraterOrEqual(ArgumentMatchers.anyInt())).thenReturn(true);
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(mockedJobScheduler);
        when(mockedJobScheduler.getPendingJob(ArgumentMatchers.anyInt())).thenReturn(mockedJobInfo);

        assertTrue(jobUtils.isWallpaperWorkScheduled());
    }

    @Test
    public void isWallpaperWorkScheduledNegative() {
        when(mockedVersion.isApiVersionGraterOrEqual(ArgumentMatchers.anyInt())).thenReturn(true);
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(mockedJobScheduler);
        when(mockedJobScheduler.getPendingJob(ArgumentMatchers.anyInt())).thenReturn(null);

        assertFalse(jobUtils.isWallpaperWorkScheduled());
    }

    @Test
    public void isWallpaperWorkScheduledPositiveSupport() {
        when(mockedVersion.isApiVersionGraterOrEqual(ArgumentMatchers.anyInt())).thenReturn(false);
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(mockedJobScheduler);
        when(mockedJobInfo.getId()).thenReturn(Const.WALLPAPER_JOB_ID);
        when(mockedJobScheduler.getAllPendingJobs()).thenReturn(Arrays.asList(mockedJobInfo));

        assertTrue(jobUtils.isWallpaperWorkScheduled());
    }

    @Test
    public void isWallpaperWorkScheduledNegativeSupport() {
        when(mockedVersion.isApiVersionGraterOrEqual(ArgumentMatchers.anyInt())).thenReturn(false);
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(mockedJobScheduler);

        assertFalse(jobUtils.isWallpaperWorkScheduled());
    }

    @Test
    public void killWallpaperJobPositive() {
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(mockedJobScheduler);
        jobUtils.killWallpaperJob();
        verify(mockedJobScheduler).cancel(ArgumentMatchers.anyInt());
        verifyNoMoreInteractions(mockedJobScheduler);
    }

    @Test
    public void killWallpaperJobNegative() {
        when(mockedContext.getSystemService(ArgumentMatchers.anyString())).thenReturn(null);
        jobUtils.killWallpaperJob();
        verifyZeroInteractions(mockedJobScheduler);
    }
}