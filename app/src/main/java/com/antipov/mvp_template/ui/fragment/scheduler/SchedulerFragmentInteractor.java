package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.pojo.Preferences;
import com.antipov.mvp_template.ui.base.IBaseInteractor;

import rx.Observable;

public interface SchedulerFragmentInteractor extends IBaseInteractor {
    Observable<Preferences> loadPrefsData();

    boolean savePrefs(Preferences preferences);
}
