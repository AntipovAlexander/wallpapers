package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.ui.base.BasePresenter;

import javax.inject.Inject;

public class SchedulerFragmentPresenterImpl <V extends SchedulerFragmentView, I extends SchedulerFragmentInteractor>
        extends BasePresenter<V, I> implements SchedulerFragmentPresenter<V, I> {

    @Inject
    public SchedulerFragmentPresenterImpl(I interactor) {
        super(interactor);
    }
}
