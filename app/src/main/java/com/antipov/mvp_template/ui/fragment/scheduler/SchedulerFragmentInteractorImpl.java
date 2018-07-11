package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.pojo.Preferences;
import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.SharedPrefs;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;

public class SchedulerFragmentInteractorImpl extends BaseInteractor implements SchedulerFragmentInteractor {
    private final SharedPrefs mPrefs;

    @Inject
    public SchedulerFragmentInteractorImpl(SchedulerProvider scheduler, SharedPrefs prefs) {
        super(scheduler);
        this.mPrefs = prefs;
    }

    @Override
    public Observable<Preferences> loadPrefsData() {
        return Observable.just(mPrefs.getPreferences());
    }

    @Override
    public boolean savePrefs(Preferences preferences) {
        return mPrefs.save(preferences);
    }
}
