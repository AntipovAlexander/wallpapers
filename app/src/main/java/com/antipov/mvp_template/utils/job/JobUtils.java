package com.antipov.mvp_template.utils.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;

import com.antipov.mvp_template.utils.Version;

import static com.antipov.mvp_template.common.Const.WALLPAPER_JOB_ID;

public class JobUtils {

    private Version version;
    private Context context;

    public JobUtils(Version version, Context context) {
        this.version = version;
        this.context = context;
    }

    public boolean isWallpaperWorkScheduled() {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        // check for compability
        if (version.isApiVersionGraterOrEqual(Build.VERSION_CODES.N)) {
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

    public void killWallpaperJob() {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(WALLPAPER_JOB_ID);
        }
    }
}
