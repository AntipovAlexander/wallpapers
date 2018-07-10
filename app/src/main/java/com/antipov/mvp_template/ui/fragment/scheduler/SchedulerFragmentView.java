package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.ui.base.IBaseView;

import java.util.Set;

public interface SchedulerFragmentView extends IBaseView {
    void makeLayoutForRandomTag();

    void makeLayoutForCustomTag();

    void setAllEnabled();

    void starJob(boolean useCustomTag, boolean useCustomTag1, boolean loadOnlyWhenWifi, Set<String> wallpaperTags, String keywordForWallpapers, int wallpaperChangesFrequency);
}
