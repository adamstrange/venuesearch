package com.strangea.venuesearch.api;

import com.strangea.venuesearch.BuildConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Config(sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class ApiManagerTest {

    ApiManager classUnderTest;

    @Before
    public void setUp() {
        ApiManager.setInstance(null);
        classUnderTest = ApiManager.getApiManager();
    }

    @Test
    public void testNotNull() {
        assertNotNull(classUnderTest);
    }

    @Test
    public void testSearch(){
        String test = "test";
        FourSquareService service = mock(FourSquareService.class);
        classUnderTest.service = service;
        when(classUnderTest.service.search(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, test)).thenReturn(Single.just(test));
        assertEquals(classUnderTest.getVenueList(test).blockingGet(), test);

    }
}