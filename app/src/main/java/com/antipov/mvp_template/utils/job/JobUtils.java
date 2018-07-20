package com.antipov.mvp_template.utils.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;

import com.antipov.mvp_template.common.Const;

import static com.antipov.mvp_template.common.Const.WALLPAPER_JOB_ID;

public class JobUtils {
    public static boolean isWallpapperWorkScheduled(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        // check for compability
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (scheduler != null) {
                JobInfo job = scheduler.getPendingJob(WALLPAPER_JOB_ID);
                return job != null;
            }
        } else {
            if (scheduler != null){
                for (JobInfo jobInfo: scheduler.getAllPendingJobs()) {
                    if (jobInfo.getId() == WALLPAPER_JOB_ID) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void killWallpaperJob(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(WALLPAPER_JOB_ID);
        }
    }
}
