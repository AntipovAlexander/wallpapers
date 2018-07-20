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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.Application;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Preferences;
import com.antipov.mvp_template.service.job.change_wallpaper.ChangeWallpaperJob;
import com.antipov.mvp_template.ui.base.BasePreferenceFragment;
import com.antipov.mvp_template.utils.DialogUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class SchedulerFragment extends BasePreferenceFragment implements SchedulerFragmentView {

    @Inject
    SchedulerFragmentPresenter<SchedulerFragmentView, SchedulerFragmentInteractor> mPresenter;
    private MultiSelectListPreference mWallpaperTags;
    private SwitchPreference mRandomTag;
    private SwitchPreference mCustomTag;
    private EditTextPreference mTagText;
    private ListPreference mFrequency;
    private CheckBoxPreference mOnlyWifi;

    private Preferences preferences;
    private Preference mDisableScheduling;
    private boolean isScheduled = false;

    public static SchedulerFragment newInstance(boolean isScheduled) {
        Bundle args = new Bundle();
        args.putBoolean(Const.Args.IS_SCHEDULED, isScheduled);
        SchedulerFragment fragment = new SchedulerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Application) getActivity().getApplication())
                .getComponent()
                .inject(this);
        mPresenter.attachView(this);
        mPresenter.loadPrefsData();
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
        mDisableScheduling = getPreferenceScreen().findPreference(getString(R.string.prefs_key_disable_scheduling));
    }

    @Override
    public void initPreferencesScreen(Preferences preferences) {
        this.preferences = preferences;

        mWallpaperTags.setValues(preferences.getWallpaperTags());
        mRandomTag.setChecked(preferences.isRandom());
        mCustomTag.setChecked(preferences.isCustom());
        mTagText.setText(preferences.getWallpaperKey());
        mFrequency.setValue(String.valueOf(preferences.getFrequency()));
        mOnlyWifi.setChecked(preferences.isOnlyWiFi());

        mPresenter.resolveEnabledPreferences(
                preferences.isCustom(),
                preferences.isRandom()
        );

        mPresenter.resolveWallpaperCustomTagSummary(preferences.getWallpaperKey());
        mPresenter.resolveWallpaperTagsSummary(preferences.getWallpaperTags());
        mPresenter.resolveWallpaperChangeFrequencySummary(preferences.getFrequency());

        mPresenter.resolveDisableScheduling(isScheduled);
    }

    @Override
    public void initListeners() {
        mWallpaperTags.setOnPreferenceChangeListener(((preference, newValue) -> {
            preferences.setWallpaperTags((Set<String>) newValue);
            mPresenter.resolveWallpaperTagsSummary(preferences.getWallpaperTags());
            return true;
        }));

        mTagText.setOnPreferenceChangeListener(((preference, newValue) -> {
            preferences.setWallpaperKey((String) newValue);
            mPresenter.resolveWallpaperCustomTagSummary(preferences.getWallpaperKey());
            return true;
        }));

        mFrequency.setOnPreferenceChangeListener(((preference, newValue) -> {
            preferences.setFrequency(Integer.parseInt((String) newValue));
            mPresenter.resolveWallpaperChangeFrequencySummary(preferences.getFrequency());
            return true;
        }));

        mCustomTag.setOnPreferenceChangeListener((preference, newValue) -> {
            preferences.setCustom((boolean) newValue);
            mPresenter.onPreferenceChange(
                    preference,
                    newValue,
                    getString(R.string.prefs_key_random_flag),
                    getString(R.string.prefs_key_custom_tag_flag)
            );
            return true;
        });

        mRandomTag.setOnPreferenceChangeListener((preference, newValue) -> {
            preferences.setRandom((boolean) newValue);
            mPresenter.onPreferenceChange(
                    preference,
                    newValue,
                    getString(R.string.prefs_key_random_flag),
                    getString(R.string.prefs_key_custom_tag_flag)
            );
            return true;
        });

        mOnlyWifi.setOnPreferenceChangeListener((preference, newValue) -> {
            preferences.setOnlyWiFi((boolean) newValue);
            return true;
        });

        mDisableScheduling.setOnPreferenceClickListener((view)->{
            return true;
        });
    }

    @Override
    public void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            isScheduled = args.getBoolean(Const.Args.IS_SCHEDULED);
        }
    }

    @Override
    public void showLoadingFullScreen() {

    }

    @Override
    public void hideLoadingFullScreen() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.schedule_wallpaper:
                mPresenter.onApplyClicked(preferences);
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
    public void setSummaryForTags(Set<String> wallpaperTags) {
        mWallpaperTags.setSummary(getString(R.string.selected, TextUtils.join(", ", wallpaperTags)));
    }

    @Override
    public void setDefaultForDisableScheduling() {
        mDisableScheduling.setSummary(R.string.disable_scheduling_summary_not_enabled);
    }

    @Override
    public void setSummaryForDisableScheduling() {
        mDisableScheduling.setSummary(R.string.disable_scheduling_summary_enabled);
    }

    @Override
    public void disableDisableScheduling() {
        mDisableScheduling.setEnabled(false);
    }

    @Override
    public void enableDisableScheduling() {
        mDisableScheduling.setEnabled(true);
    }

    @Override
    public void starJob(Preferences preferences) {
        // start scheduling wallpapers change
        if (getBaseActivity() != null) {

            ComponentName serviceName = new ComponentName(getBaseActivity(), ChangeWallpaperJob.class);
            JobInfo.Builder jobInfo = new JobInfo.Builder(Const.WALLPAPER_JOB_ID, serviceName)
                    .setPersisted(true)
                    .setRequiresDeviceIdle(false)
                    .setPeriodic(TimeUnit.HOURS.toMillis(preferences.getFrequency()));

            // resolving network type
            if (preferences.isOnlyWiFi()) {
                jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            } else {
                jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            }

            JobScheduler scheduler = (JobScheduler) getBaseActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (scheduler != null) {
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
