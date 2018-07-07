package com.antipov.mvp_template.ui.base;

import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Scheduler;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class BaseInteractor implements IBaseInteractor {


    private SchedulerProvider mScheduler;

    @Inject
    public BaseInteractor(SchedulerProvider scheduler) {
        this.mScheduler = scheduler;
    }

    public Scheduler io(){
        return mScheduler.io();
    }

    public Scheduler ui(){
        return mScheduler.ui();
    }

    public Scheduler computation(){
        return mScheduler.computation();
    }

    public Scheduler newThread(){
        return mScheduler.newThread();
    }
}
