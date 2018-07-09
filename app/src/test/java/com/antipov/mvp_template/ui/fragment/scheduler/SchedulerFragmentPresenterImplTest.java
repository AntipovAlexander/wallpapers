package com.antipov.mvp_template.ui.fragment.scheduler;

import android.preference.Preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
    public void resolveEnabledPreferencesForNothing() {
        mPresenter.resolveEnabledPreferences(
                false,
                false
        );
        verifyZeroInteractions(mMockedView);
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
        verify(mMockedView).setAllEnabled();
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
        verify(mMockedView).setAllEnabled();
        verifyNoMoreInteractions(mMockedView);
    }
}