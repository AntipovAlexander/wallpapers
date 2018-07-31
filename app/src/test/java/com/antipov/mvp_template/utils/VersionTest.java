package com.antipov.mvp_template.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.*;

public class VersionTest {

    Version version;

    @Before
    public void setUp() throws Exception {
        version = new Version();
    }

    @Test
    public void getApiVersion() {
        assert version.getApiVersion() == ArgumentMatchers.anyInt();
    }

    @Test
    public void isApiVersionGraterOrEqual() {
        assert version.isApiVersionGraterOrEqual(1) == ArgumentMatchers.anyBoolean();
    }
}