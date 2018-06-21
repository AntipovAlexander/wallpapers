package com.antipov.mvp_template.ui.activity.main;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void secondActivityStart() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.rv_photos)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(PhotoDetailActivity.class.getName()));
    }

}