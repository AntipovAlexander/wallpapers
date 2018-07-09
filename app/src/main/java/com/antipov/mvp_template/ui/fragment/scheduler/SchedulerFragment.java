package com.antipov.mvp_template.ui.fragment.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.service.ChangeWallpaperService;
import com.antipov.mvp_template.ui.base.BasePreferenceFragment;
import com.antipov.mvp_template.utils.SharedPrefs;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class SchedulerFragment extends BasePreferenceFragment implements SchedulerFragmentView, Preference.OnPreferenceChangeListener {

    @Inject SharedPrefs prefs;
    @Inject SchedulerFragmentPresenter<SchedulerFragmentView, SchedulerFragmentInteractor> mPresenter;
    private MultiSelectListPreference mWallpaperTags;
    private SwitchPreference mRandomTag;
    private SwitchPreference mCustomTag;
    private EditTextPreference mTagText;
    private ListPreference mFrequency;
    private CheckBoxPreference mOnlyWifi;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Application)getActivity().getApplication())
                .getComponent()
                .inject(this);
        mPresenter.attachView(this);
        mPresenter.resolveEnabledPreferences(
                prefs.isUseCustomTag(),
                prefs.isUseRandomTag()
        );
        setHasOptionsMenu(true);
    }

    @Override
    public int getPreferenceXml() {
        return R.xml.preferences;
    }

    @Override
    public void initViews() {
        mWallpaperTags = (MultiSelectListPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_tags));
        mRandomTag = (SwitchPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_random_flag));
        mCustomTag = (SwitchPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_custom_tag_flag));
        mTagText = (EditTextPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_keyword));
        mFrequency = (ListPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_frequency));
        mOnlyWifi = (CheckBoxPreference) getPreferenceScreen().findPreference(getString(R.string.prefs_key_onlywifi));
    }

    @Override
    public void initListeners() {
        mCustomTag.setOnPreferenceChangeListener(this);
        mRandomTag.setOnPreferenceChangeListener(this);
    }

    @Override
    public void showLoadingFullScreen() {

    }

    @Override
    public void hideLoadingFullScreen() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.schedule_wallpaper:
                if (getBaseActivity() != null) {
                    ComponentName serviceName = new ComponentName(getBaseActivity(), ChangeWallpaperService.class);
                    JobInfo jobInfo = new JobInfo.Builder(0, serviceName)
                                .setPeriodic(TimeUnit.SECONDS.toMillis(10))
                                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                .build();

                    JobScheduler scheduler = (JobScheduler) getBaseActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    if (scheduler != null){
                        scheduler.schedule(jobInfo);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void makeLayoutForRandomTag() {
        mWallpaperTags.setEnabled(false);
        mCustomTag.setEnabled(false);
        mTagText.setEnabled(false);
        mRandomTag.setEnabled(true);
    }

    @Override
    public void makeLayoutForCustomTag() {
        mWallpaperTags.setEnabled(false);
        mCustomTag.setEnabled(true);
        mTagText.setEnabled(true);
        mRandomTag.setEnabled(false);
    }

    @Override
    public void setAllEnabled() {
        mWallpaperTags.setEnabled(true);
        mCustomTag.setEnabled(true);
        mTagText.setEnabled(true);
        mRandomTag.setEnabled(true);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        mPresenter.onPreferenceChange(
                preference,
                newValue,
                getString(R.string.prefs_key_random_flag),
                getString(R.string.prefs_key_custom_tag_flag)
        );
        return true;
    }
}
