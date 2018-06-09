package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.udacity.gradle.builditbigger.backend.myApi.model.JokeBean;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NonEmptyStringTest {
    private static final String LOG_TAG = NonEmptyStringTest.class.getSimpleName();

    @Test
    public void jokeLoaderActivityTest() {
        Log.d(LOG_TAG, "Running test: jokeLoaderActivityTest");
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        final JokeBean[] result = {null};
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.OnResult() {
            @Override
            public void endpointResult(JokeBean jokeBean) {
                //Do nothing
            }
        });
        endpointsAsyncTask.execute();
        try {
            result[0] = endpointsAsyncTask.get();
            Log.i(LOG_TAG, "Success retrieving a non-empty string: " + result[0]);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while retrieving a non-empty string: ", e);
        }
        assertNotNull(result[0]);
        assertNotNull(result[0].getQuery());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
