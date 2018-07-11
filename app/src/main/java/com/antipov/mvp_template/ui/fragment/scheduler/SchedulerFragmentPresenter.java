package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import com.antipov.mvp_template.ui.base.IBasePresenter;

import java.util.Set;

public interface SchedulerFragmentPresenter <V extends SchedulerFragmentView, I extends SchedulerFragmentInteractor>
        extends IBasePresenter<V, I> {
    void resolveEnabledPreferences(boolean useCustomTag, boolean useRandomTag);

    void onPreferenceChange(Preference preference, Object newValue, String randomKey, String customKey);

    void onApplyClicked(boolean useRandomTag, boolean useCustomTag, boolean loadOnlyWhenWifi,
                        Set<String> wallpaperTags, String keywordForWallpapers, int wallpaperChangesFrequency);

    void resolveWallpaperCustomTagSummary(String keywordForWallpapers);

    void resolveWallpaperTagsSummary(Set<String> wallpaperTags);

    void resolveWallpaperChangeFrequencySummary(int keywordForWallpapers);
}
