package com.antipov.mvp_template.ui.fragment.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.service.change_wallpaper.ChangeWallpaperService;
import com.antipov.mvp_template.ui.base.BasePreferenceFragment;
import com.antipov.mvp_template.utils.SharedPrefs;

import java.util.Set;
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
    private int JOBID = 112233;

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
                mPresenter.onApplyClicked(
                        prefs.isUseRandomTag(),
                        prefs.isUseCustomTag(),
                        prefs.isLoadOnlyWhenWifi(),
                        prefs.getWallpaperTags(),
                        prefs.getKeywordForWallpapers(),
                        prefs.getWallpaperChangesFrequency()
                );
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
    public void starJob(boolean useRandomTag, boolean useCustomTag, boolean loadOnlyWhenWifi,
                        Set<String> wallpaperTags, String keywordForWallpapers,
                        int wallpaperChangesFrequency) {
        // start scheduling wallpapers change
        if (getBaseActivity() != null) {

            ComponentName serviceName = new ComponentName(getBaseActivity(), ChangeWallpaperService.class);
            JobInfo.Builder jobInfo = new JobInfo.Builder(JOBID, serviceName)
                    .setPersisted(true)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(15));
//                    .setPeriodic(TimeUnit.HOURS.toMillis(wallpaperChangesFrequency))

            // resolving network type
            if (loadOnlyWhenWifi) {
                jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            } else {
                jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            }

            JobScheduler scheduler = (JobScheduler) getBaseActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (scheduler != null){
                scheduler.schedule(jobInfo.build());
            }
        }
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

    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }
}
