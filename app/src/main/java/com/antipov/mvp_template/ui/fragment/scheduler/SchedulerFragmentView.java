package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.pojo.Preferences;
import com.antipov.mvp_template.ui.base.IBaseView;

import java.util.Set;

public interface SchedulerFragmentView extends IBaseView {
    void makeLayoutForRandomTag();

    void makeLayoutForCustomTag();

    void resetToDefaults();

    void starJob(Preferences preferences);

    void setSummaryForKeyword(String keywordForWallpapers);

    void setDefaultSummaryForKeyword();

    void setSummaryForFrequency(int frequency);

    void setDefaultSummaryForFrequency();

    void setSummaryForFrequencyDaily();

    void setDefaultSummaryForTags();

    void setSummaryForTags(Set<String> wallpaperTags);

    void initPreferencesScreen(Preferences preferences);
}
