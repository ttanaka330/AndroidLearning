package jp.ttanaka330.androidlearning;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();

        if (BuildConfig.DEBUG) {
            assertEquals("jp.ttanaka330.androidlearning.debug", appContext.getPackageName());
        } else {
            assertEquals("jp.ttanaka330.androidlearning", appContext.getPackageName());
        }
    }
}
