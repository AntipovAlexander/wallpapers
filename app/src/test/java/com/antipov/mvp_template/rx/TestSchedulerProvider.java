package com.antipov.mvp_template.rx;

import com.antipov.mvp_template.utils.rx.SchedulerProvider;


import rx.Scheduler;
import rx.schedulers.TestScheduler;


/**
 * Created by janisharali on 21/07/17.
 */

public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler mTestScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.mTestScheduler = testScheduler;
    }

    @Override
    public Scheduler ui() {
        return mTestScheduler;
    }

    @Override
    public Scheduler computation() {
        return mTestScheduler;
    }

    @Override
    public Scheduler io() {
        return mTestScheduler;
    }

    @Override
    public Scheduler newThread() {
        return mTestScheduler;
    }

}

