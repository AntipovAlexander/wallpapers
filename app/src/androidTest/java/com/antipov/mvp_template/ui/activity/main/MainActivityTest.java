package com.antipov.mvp_template.ui.activity.main;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.application.App;
import com.antipov.mvp_template.di.component.DaggerAppComponent;
import com.antipov.mvp_template.di.module.AppModule;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Mock
    MainInteractorImpl mMockInteractor;

    class MockMainInteractorModule extends AppModule{
        @Override
        public MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
            return mMockInteractor;
        }
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);

    @Before
    public void setUp() {
        Intents.init();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        App app = (App) instrumentation.getTargetContext().getApplicationContext();
        app.setComponent(
                DaggerAppComponent.builder().appModule(new MockMainInteractorModule()).build()
        );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void secondActivityStart() {
        Picture picture = new Picture();
        Mockito.when(mMockInteractor.getPictures()).thenReturn(Observable.just(picture.getListForTest()));
        mActivityRule.launchActivity(null);
        onView(withId(R.id.rv_photos)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(PhotoDetailActivity.class.getName()));
    }

}