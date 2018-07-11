package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerFragmentPresenterImplTest {

    @Mock
    SchedulerFragmentView mMockedView;

    @Mock
    SchedulerFragmentInteractor mMockedInteractor;

    @Mock
    Preference preference;

    SchedulerFragmentPresenter<SchedulerFragmentView, SchedulerFragmentInteractor> mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new SchedulerFragmentPresenterImpl<>(mMockedInteractor);
        mPresenter.attachView(mMockedView);
    }

    @After
    public void tearDown() throws Exception {
        mPresenter.detachView();
    }

    @Test
    public void resolveEnabledPreferencesForCustom() {
        mPresenter.resolveEnabledPreferences(
                true,
                false
        );
        verify(mMockedView).makeLayoutForCustomTag();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void resolveEnabledPreferencesForRandom() {
        mPresenter.resolveEnabledPreferences(
                false,
                true
        );
        verify(mMockedView).makeLayoutForRandomTag();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void resolveDefaultEnabledPreferences() {
        mPresenter.resolveEnabledPreferences(
                false,
                false
        );
        verify(mMockedView).resetToDefaults();
    }

    @Test
    public void onUseRandomPreferenceChange() {
        String keyRandom = "key_rand";
        Boolean isSelected = true;
        when(preference.getKey()).thenReturn(keyRandom);
        mPresenter.onPreferenceChange(preference, isSelected, keyRandom, "");
        verify(mMockedView).makeLayoutForRandomTag();
        isSelected = false;
        mPresenter.onPreferenceChange(preference, isSelected, keyRandom, "");
        verify(mMockedView).resetToDefaults();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void onUseCustomPreferenceChange() {
        String keyCustom = "key_custom";
        Boolean isSelected = true;
        when(preference.getKey()).thenReturn(keyCustom);
        mPresenter.onPreferenceChange(preference, isSelected, "", keyCustom);
        verify(mMockedView).makeLayoutForCustomTag();
        isSelected = false;
        mPresenter.onPreferenceChange(preference, isSelected, "", keyCustom);
        verify(mMockedView).resetToDefaults();
        verifyNoMoreInteractions(mMockedView);
    }

    @Test
    public void onApplyWithoutFrequency(){
        mPresenter.onApplyClicked(
                true,
                false,
                false,
                new HashSet<String>(),
                "",
                0

        );
        verify(mMockedView).onError(ArgumentMatchers.anyInt());
    }

    @Test
    public void onApplyWithoutCustomKeyword(){
        mPresenter.onApplyClicked(
                false,
                true,
                false,
                new HashSet<String>(),
                "",
                1

        );
        verify(mMockedView).onError(ArgumentMatchers.anyInt());
    }

    @Test
    public void onApplyWithoutTags(){
        mPresenter.onApplyClicked(
                false,
                false,
                false,
                new HashSet<>(),
                "foo",
                1

        );
        verify(mMockedView).onError(ArgumentMatchers.anyInt());
    }

    @Test
    public void onApplyPositiveRandom(){
        mPresenter.onApplyClicked(
                true,
                false,
                false,
                new HashSet<>(),
                "foo",
                1

        );
        verify(mMockedView).starJob(
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anySet(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt());
    }

    @Test
    public void onApplyPositiveCustom(){
        mPresenter.onApplyClicked(
                false,
                true,
                false,
                new HashSet<>(),
                "foo",
                1

        );
        verify(mMockedView).starJob(
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anySet(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt());
    }

    @Test
    public void onApplyPositiveWithTags(){
        Set<String> set = new HashSet<>();
        set.add("string");
        mPresenter.onApplyClicked(
                false,
                true,
                false,
                 set,
                "foo",
                1

        );
        verify(mMockedView).starJob(
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.anySet(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt());
    }

    @Test
    public void resolveWallpaperCustomTagSummary(){
        mPresenter.resolveWallpaperCustomTagSummary("");
        verify(mMockedView).setDefaultSummaryForKeyword();
        mPresenter.resolveWallpaperCustomTagSummary("fsdfsfsd");
        verify(mMockedView).setSummaryForKeyword(ArgumentMatchers.anyString());
    }

    @Test
    public void resolveWallpaperTagsSummary(){
        Set<String> s = new HashSet<>();
        mPresenter.resolveWallpaperTagsSummary(s);
        verify(mMockedView).setDefaultSummaryForTags();
        s.add("foo");
        mPresenter.resolveWallpaperTagsSummary(s);
        verify(mMockedView).setSummaryForTags(ArgumentMatchers.anySet());
    }

    @Test
    public void resolveWallpaperChangeFrequencySummary(){
        mPresenter.resolveWallpaperChangeFrequencySummary(0);
        verify(mMockedView).setDefaultSummaryForFrequency();
        mPresenter.resolveWallpaperChangeFrequencySummary(1);
        verify(mMockedView).setSummaryForFrequency(ArgumentMatchers.anyInt());
        mPresenter.resolveWallpaperChangeFrequencySummary(12);
        verify(mMockedView).setDefaultSummaryForFrequency();
    }

}