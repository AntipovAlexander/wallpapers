package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class SchedulerFragmentInteractorImpl extends BaseInteractor implements SchedulerFragmentInteractor {
    @Inject
    public SchedulerFragmentInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }
}
