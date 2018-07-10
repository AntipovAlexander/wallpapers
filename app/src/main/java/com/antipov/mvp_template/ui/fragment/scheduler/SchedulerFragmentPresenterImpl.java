package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.ui.base.BasePresenter;
import java.util.Set;
import javax.inject.Inject;

public class SchedulerFragmentPresenterImpl<V extends SchedulerFragmentView, I extends SchedulerFragmentInteractor>
        extends BasePresenter<V, I> implements SchedulerFragmentPresenter<V, I> {

    @Inject
    public SchedulerFragmentPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void resolveEnabledPreferences(boolean useCustomTag, boolean useRandomTag) {
        if (!isViewAttached()) return;
        if (useRandomTag) {
            getView().makeLayoutForRandomTag();
        } else if (useCustomTag) {
            getView().makeLayoutForCustomTag();
        }
    }

    @Override
    public void onPreferenceChange(Preference preference, Object newValue, final String randomKey, String customKey) {
        if (!isViewAttached()) return;
        if (newValue instanceof Boolean) {
            Boolean isSelected = (Boolean) newValue;

            // if random key pref changed
            if (preference.getKey().equals(randomKey)) {
                if (isSelected) {
                    getView().makeLayoutForRandomTag();
                } else {
                    getView().setAllEnabled();
                }
            }

            // if custom key pref changed
            if (preference.getKey().equals(customKey)) {
                if (isSelected) {
                    getView().makeLayoutForCustomTag();
                } else {
                    getView().setAllEnabled();
                }
            }
        }
    }

    @Override
    public void onApplyClicked(boolean useRandomTag, boolean useCustomTag, boolean loadOnlyWhenWifi,
                               Set<String> wallpaperTags, String keywordForWallpapers,
                               int wallpaperChangesFrequency) {
        if (!isViewAttached()) return;

        // validating frequency
        if (!validateFrequency(wallpaperChangesFrequency)) {
            getView().onError(R.string.select_frequency);
            return;
        }

        // if user using custom tag we must validate that keyword was entered
        if (useCustomTag && !validateKeyword(keywordForWallpapers)){
            getView().onError(R.string.enter_keyword);
            return;
        }

        // if user wants getting images by provided tags, validate that array with tags isnt empty
        if (!useCustomTag && !useRandomTag && wallpaperTags.size() == 0){
            getView().onError(R.string.select_tags);
            return;
        }

        getView().starJob(
                useRandomTag,
                useCustomTag,
                loadOnlyWhenWifi,
                wallpaperTags,
                keywordForWallpapers,
                wallpaperChangesFrequency
        );
    }

    private boolean validateKeyword(String keywordForWallpapers) {
        return !keywordForWallpapers.isEmpty();
    }

    private boolean validateFrequency(int f){
        return f > 0;
    }
}
