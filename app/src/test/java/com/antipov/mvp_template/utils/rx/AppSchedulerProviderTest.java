package com.antipov.mvp_template.utils.rx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AppSchedulerProviderTest {

    SchedulerProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new AppSchedulerProvider();
    }

    /**
     * ExceptionInInitializerError will be thrown if call android main thread
     */
    @Test(expected = ExceptionInInitializerError.class)
    public void ui() {
        provider.ui();
    }

    @Test
    public void computation() {
        assert provider.computation() != null;
    }

    @Test
    public void io() {
        assert provider.io() != null;
    }

    @Test
    public void newThread() {
        assert provider.newThread() != null;
    }
}