package com.antipov.mvp_template.utils.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;

import com.antipov.mvp_template.common.Const;

public class JobScheduledChecker {
    public static boolean isWallpapperWorkScheduled(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        // check for compability
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (scheduler != null) {
                JobInfo job = scheduler.getPendingJob(Const.WALLPAPER_JOB_ID);
                if (job != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (scheduler != null){
                for (JobInfo jobInfo: scheduler.getAllPendingJobs()) {
                    if (jobInfo.getId() == Const.WALLPAPER_JOB_ID) {
                        return true;
                    }
                    return false;
                }
            }
        }

        return false;
    }
}
