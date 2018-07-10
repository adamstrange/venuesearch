package com.strangea.venuesearch;

import android.view.View;

import com.strangea.venuesearch.api.ApiManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Config(sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Rule
    public test.SchedulerRule ruleTest = new test.SchedulerRule();

    private MainActivity classUnderTest;

    @Before
    public void setup() {
        classUnderTest = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testNotNull() {
        assertNotNull(classUnderTest);
    }

    @Test
    public void testSearchEmpty(){
        classUnderTest.search();
        assertEquals(classUnderTest.textView.getText(), RuntimeEnvironment.application.getString(R.string.search_location_required));
    }

    @Test
    public void testSearchSuccessful(){
        String test = "{\n" +
                "  \"test\": \"some string\"\n" +
                "}";

        ApiManager apiManager = mock(ApiManager.class);
        when(apiManager.getVenueList(any(String.class))).thenReturn(Single.just(test));
        ApiManager.setInstance(apiManager);

        classUnderTest.editText.setText("London");
        classUnderTest.search();
        assertEquals(classUnderTest.textView.getText().toString(), test);
        assertEquals(classUnderTest.progressView.getVisibility(), View.GONE);
    }

    @Test
    public void testSearchError(){
        String test = "There's been an error";

        ApiManager apiManager = mock(ApiManager.class);
        when(apiManager.getVenueList(any(String.class))).thenReturn(Single.<String>error(new Throwable(test)));
        ApiManager.setInstance(apiManager);

        classUnderTest.editText.setText("London");
        classUnderTest.search();
        assertEquals(classUnderTest.textView.getText().toString(), test);
        assertEquals(classUnderTest.progressView.getVisibility(), View.GONE);
    }

}