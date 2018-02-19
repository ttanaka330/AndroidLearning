package jp.ttanaka330.androidlearning.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ファイルを取り扱うユーティリティクラスです。
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * アセット内の CSV ファイルを読み込みます。
     *
     * @param context        コンテキスト（リソース取得に使用）
     * @param assetsFileName アセットファイル名
     * @return CSV ファイルデータ
     * @throws IOException ファイル読み込みに失敗した場合に呼ばれます。
     */
    @NonNull
    public static List<String> readAssetsCsv(@NonNull Context context,
                                             @NonNull String assetsFileName)
            throws IOException {
        List<String> list = new ArrayList<>();
        AssetManager assetManager = context.getResources().getAssets();
        try (InputStream in = assetManager.open(assetsFileName);
             InputStreamReader isr = new InputStreamReader(in);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

}
