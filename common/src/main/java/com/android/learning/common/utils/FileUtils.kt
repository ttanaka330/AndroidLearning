package com.android.learning.common.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileUtils {

    /**
     * アセット内の CSV ファイルを読み込みます。
     *
     * @param context コンテキスト（リソース取得に使用）
     * @param assetsFileName アセットファイル名
     * @return CSV ファイルデータ
     * @throws IOException ファイル読み込みに失敗した場合に呼ばれます。
     */
    @JvmStatic
    @Throws(IOException::class)
    fun readAssetsCsv(
        context: Context,
        assetsFileName: String
    ): List<String> {
        val list = ArrayList<String>()
        val assetManager = context.resources.assets
        assetManager.open(assetsFileName).use { `is` ->
            InputStreamReader(`is`).use { isr ->
                BufferedReader(isr).use { br ->
                    while (br.readLine()?.let { list.add(it) } != null);
                }
            }
        }
        return list
    }
}
