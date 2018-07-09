package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import com.antipov.mvp_template.ui.base.BasePresenter;

import javax.inject.Inject;

public class SchedulerFragmentPresenterImpl<V extends SchedulerFragmentView, I extends SchedulerFragmentInteractor>
        extends BasePresenter<V, I> implements SchedulerFragmentPresenter<V, I> {

    @Inject
    public SchedulerFragmentPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void resolveEnabledPreferences(boolean useCustomTag, boolean useRandomTag) {
        if (!isViewAttached()) return;
        if (useRandomTag) {
            getView().makeLayoutForRandomTag();
        } else if (useCustomTag) {
            getView().makeLayoutForCustomTag();
        }
    }

    @Override
    public void onPreferenceChange(Preference preference, Object newValue, final String randomKey, String customKey) {
        if (!isViewAttached()) return;
        if (newValue instanceof Boolean) {
            Boolean isSelected = (Boolean) newValue;

            // if random key pref changed
            if (preference.getKey().equals(randomKey)) {
                if (isSelected) {
                    getView().makeLayoutForRandomTag();
                } else {
                    getView().setAllEnabled();
                }
            }

            // if custom key pref changed
            if (preference.getKey().equals(customKey)) {
                if (isSelected) {
                    getView().makeLayoutForCustomTag();
                } else {
                    getView().setAllEnabled();
                }
            }
        }
    }
}
