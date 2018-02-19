package jp.ttanaka330.androidlearning.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class FileUtilsTest {

    @Test
    public void readAssetsCsv() throws Exception {
        Context context = InstrumentationRegistry.getContext();

        List<String> textLines = null;
        try {
            textLines = FileUtils.readAssetsCsv(context, "unknown.txt");
            fail("存在しないファイルが読み取られました。");
        } catch (IOException e) {
            assertTrue(e instanceof FileNotFoundException);
            assertNull(textLines);
        }

        final List<String> EXPECTED = new ArrayList<String>() {{
            add("1234567890");
            add("abcdefghijklmnopqrstuvwxyz");
            add("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            add("あいうえおかきくけこさしすせそたちつてとなにぬねの");
            add("ハヒフヘホマミムメモヤユヨラリルレロワヲン");
        }};
        textLines = FileUtils.readAssetsCsv(context, "fileutils_test.txt");
        assertThat(EXPECTED, contains(textLines.toArray()));
    }

}