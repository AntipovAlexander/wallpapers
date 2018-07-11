package com.antipov.mvp_template.ui.fragment.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.antipov.mvp_template.service.change_wallpaper.ChangeWallpaperService;
import com.antipov.mvp_template.ui.base.BasePreferenceFragment;
import com.antipov.mvp_template.utils.DialogUtils;
import com.antipov.mvp_template.utils.SharedPrefs;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class SchedulerFragment extends BasePreferenceFragment implements SchedulerFragmentView {

    @Inject SharedPrefs prefs;
    @Inject SchedulerFragmentPresenter<SchedulerFragmentView, SchedulerFragmentInteractor> mPresenter;
    private MultiSelectListPreference mWallpaperTags;
    private SwitchPreference mRandomTag;
    private SwitchPreference mCustomTag;
    private EditTextPreference mTagText;
    private ListPreference mFrequency;
    private CheckBoxPreference mOnlyWifi;
    private int JOBID = 112233;

    private Preference.OnPreferenceChangeListener onCheckablePreferenceChanged = (preference, newValue) -> {
        mPresenter.onPreferenceChange(
                preference,
                newValue,
                getString(R.string.prefs_key_random_flag),
                getString(R.string.prefs_key_custom_tag_flag)
        );
        return true;
    };


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
        mPresenter.resolveWallpaperCustomTagSummary(prefs.getKeywordForWallpapers());
        mPresenter.resolveWallpaperTagsSummary(prefs.getWallpaperTags());
        mPresenter.resolveWallpaperChangeFrequencySummary(prefs.getWallpaperChangesFrequency());

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
        mWallpaperTags.setOnPreferenceChangeListener(((preference, newValue) -> {
            mPresenter.resolveWallpaperTagsSummary((Set<String>) newValue);
            return true;
        }));
        mTagText.setOnPreferenceChangeListener(((preference, newValue) -> {
            mPresenter.resolveWallpaperCustomTagSummary((String) newValue);
            return true;
        }));
        mFrequency.setOnPreferenceChangeListener(((preference, newValue) -> {
            mPresenter.resolveWallpaperChangeFrequencySummary(Integer.parseInt((String) newValue));
            return true;
        }));

        mCustomTag.setOnPreferenceChangeListener(onCheckablePreferenceChanged);
        mRandomTag.setOnPreferenceChangeListener(onCheckablePreferenceChanged);
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
    public void resetToDefaults() {
        mWallpaperTags.setEnabled(true);
        mCustomTag.setEnabled(true);
        mTagText.setEnabled(false);
        mRandomTag.setEnabled(true);
    }

    @Override
    public void setSummaryForKeyword(String keywordForWallpapers) {
        mTagText.setSummary(getString(R.string.selected, keywordForWallpapers));
    }

    @Override
    public void setDefaultSummaryForKeyword() {
        mTagText.setSummary(getString(R.string.keyword_hint));
    }

    @Override
    public void setSummaryForFrequency(int frequency) {
        mFrequency.setSummary(getString(R.string.selected_hours, frequency));
    }

    @Override
    public void setDefaultSummaryForFrequency() {
        mFrequency.setSummary(getString(R.string.change_frequency_summary));
    }

    @Override
    public void setSummaryForFrequencyDaily() {
        mFrequency.setSummary(getString(R.string.frequency_daily));
    }

    @Override
    public void setDefaultSummaryForTags() {
        mWallpaperTags.setSummary(getString(R.string.wallpaper_topic_summary));
    }

    @Override
    public void setSummaryForTags(String wallpaperTags) {
        mWallpaperTags.setSummary(getString(R.string.selected, wallpaperTags));
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

            // FIXME: 10.07.18 REMOVE IT, IT ONLY FOR TESTING PURPOSES
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            DialogUtils.show(getBaseActivity(), "Scheduled successfully");
            getBaseActivity().finish();
        }
    }
}
