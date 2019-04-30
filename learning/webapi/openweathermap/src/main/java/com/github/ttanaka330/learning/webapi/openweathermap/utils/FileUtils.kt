package com.github.ttanaka330.learning.webapi.openweathermap.utils

import android.content.Context
import java.io.IOException

object FileUtils {

    /**
     * アセット内のテキストファイルを読み込みます。
     *
     * @param context コンテキスト（リソース取得に使用）
     * @param assetsFileName アセットファイル名
     * @return CSVファイルデータ
     * @throws IOException ファイル読み込みに失敗した場合に呼ばれます。
     */
    @Throws(IOException::class)
    fun readAssetsTextFile(
        context: Context,
        assetsFileName: String
    ): List<String> {
        return context.resources.assets.open(assetsFileName)
            .reader(charset = Charsets.UTF_8)
            .use { it.readLines() }
    }
}
