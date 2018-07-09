package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import com.antipov.mvp_template.ui.base.IBasePresenter;

public interface SchedulerFragmentPresenter <V extends SchedulerFragmentView, I extends SchedulerFragmentInteractor>
        extends IBasePresenter<V, I> {
    void resolveEnabledPreferences(boolean useCustomTag, boolean useRandomTag);

    void onPreferenceChange(Preference preference, Object newValue, String randomKey, String customKey);
}
