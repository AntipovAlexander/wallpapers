package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;
import android.text.TextUtils;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Preferences;
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
    public void loadPrefsData() {
        if (isViewAttached()) getView().showLoadingFullScreen();
        getInteractor()
                .loadPrefsData()
                .subscribe(
                        preferences -> {
                            if (!isViewAttached()) return;
                            getView().hideLoadingFullScreen();
                            getView().initPreferencesScreen(preferences);
                        },
                        throwable -> {
                            if (!isViewAttached()) return;
                            getView().hideLoadingFullScreen();
                            getView().onError(throwable.getMessage());
                        }
                );
    }

    @Override
    public void resolveEnabledPreferences(boolean useCustomTag, boolean useRandomTag) {
        if (!isViewAttached()) return;
        if (useRandomTag) {
            getView().makeLayoutForRandomTag();
        } else if (useCustomTag) {
            getView().makeLayoutForCustomTag();
        } else {
            getView().resetToDefaults();
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
                    getView().resetToDefaults();
                }
            }

            // if custom key pref changed
            if (preference.getKey().equals(customKey)) {
                if (isSelected) {
                    getView().makeLayoutForCustomTag();
                } else {
                    getView().resetToDefaults();
                }
            }
        }
    }

    @Override
    public void onApplyClicked(Preferences preferences) {
        if (!isViewAttached()) return;

        // validating frequency
        if (!validateFrequency(preferences.getFrequency())) {
            getView().onError(R.string.select_frequency);
            return;
        }

        // if user using custom tag we must validate that keyword was entered
        if (preferences.isCustom() && !validateKeyword(preferences.getWallpaperKey())){
            getView().onError(R.string.enter_keyword);
            return;
        }

        // if user wants getting images by provided tags, validate that array with tags isnt empty
        if (!preferences.isCustom() && !preferences.isRandom() && preferences.getWallpaperTags().size() == 0){
            getView().onError(R.string.select_tags);
            return;
        }

        // saving prefs and staring job if success
        if (getInteractor().savePrefs(preferences)){
            getView().starJob(preferences);
        } else {
            getView().onError(R.string.error_when_updating_prefs);
        }
    }

    @Override
    public void resolveWallpaperCustomTagSummary(String keywordForWallpapers) {
        if (!isViewAttached()) return;

        if (!keywordForWallpapers.isEmpty()){
            getView().setSummaryForKeyword(keywordForWallpapers);
        } else {
            getView().setDefaultSummaryForKeyword();
        }
    }

    @Override
    public void resolveWallpaperTagsSummary(Set<String> wallpaperTags) {
        if (!isViewAttached()) return;

        if (wallpaperTags.size() == 0){
            getView().setDefaultSummaryForTags();
        } else {
            getView().setSummaryForTags(wallpaperTags);
        }
    }

    @Override
    public void resolveWallpaperChangeFrequencySummary(int frequency) {
        if (!isViewAttached()) return;

        if (frequency == 0){
            getView().setDefaultSummaryForFrequency();
        } else if (frequency == 12){
            getView().setSummaryForFrequencyDaily();
        } else {
            getView().setSummaryForFrequency(frequency);
        }

    }

    private boolean validateKeyword(String keywordForWallpapers) {
        return !keywordForWallpapers.isEmpty();
    }

    private boolean validateFrequency(int f){
        return f > 0;
    }
}
