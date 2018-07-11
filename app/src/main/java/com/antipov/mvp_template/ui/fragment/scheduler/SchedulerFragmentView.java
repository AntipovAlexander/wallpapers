package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.ui.base.IBaseView;

import java.util.Set;

public interface SchedulerFragmentView extends IBaseView {
    void makeLayoutForRandomTag();

    void makeLayoutForCustomTag();

    void resetToDefaults();

    void starJob(boolean useCustomTag, boolean useCustomTag1, boolean loadOnlyWhenWifi, Set<String> wallpaperTags, String keywordForWallpapers, int wallpaperChangesFrequency);

    void setSummaryForKeyword(String keywordForWallpapers);

    void setDefaultSummaryForKeyword();

    void setSummaryForFrequency(int frequency);

    void setDefaultSummaryForFrequency();

    void setSummaryForFrequencyDaily();

    void setDefaultSummaryForTags();

    void setSummaryForTags(Set<String> wallpaperTags);
}
