package com.antipov.mvp_template.ui.fragment.scheduler;

import com.antipov.mvp_template.ui.base.IBaseView;

public interface SchedulerFragmentView extends IBaseView {
    void makeLayoutForRandomTag();

    void makeLayoutForCustomTag();

    void setAllEnabled();
}
