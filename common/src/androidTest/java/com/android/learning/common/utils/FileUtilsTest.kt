package com.android.learning.common.utils

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.io.FileNotFoundException
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FileUtilsTest {

    private val context: Context = InstrumentationRegistry.getContext()

    @Test
    @Throws(Exception::class)
    fun readAssetsCsv_Exists() {
        val textLines: List<String>? = FileUtils.readAssetsCsv(context, "FileUtils_test.txt")

        val expected: ArrayList<String> = ArrayList<String>().apply {
            add("1234567890")
            add("abcdefghijklmnopqrstuvwxyz")
            add("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
            add("あいうえおかきくけこさしすせそたちつてとなにぬねの")
            add("ハヒフヘホマミムメモヤユヨラリルレロワヲン")
        }
        assertThat(expected).containsAll(textLines)
    }

    @Test
    @Throws(Exception::class)
    fun readAssetsCsv_NotFound() {
        var textLines: List<String>? = null
        try {
            textLines = FileUtils.readAssetsCsv(context, "unknown.txt")
            fail("存在しないファイルが読み取られました。")
        } catch (e: IOException) {
            assertTrue(e is FileNotFoundException)
            assertNull(textLines)
        }
    }
}