package com.antipov.mvp_template.ui.fragment.scheduler;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.utils.SharedPrefs;

import javax.inject.Inject;

public class SchedulerFragment extends PreferenceFragment {

    @Inject SharedPrefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ((Application)getActivity().getApplication())
                .getComponent()
                .inject(this);
        "".toString();
    }
}
