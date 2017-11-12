package jp.ttanaka330.androidlearning.common.dialog;

import android.content.Intent;

/**
 * ダイアログイベントリスナー。
 * {@link android.app.Activity#onActivityResult(int, int, Intent)} を意識して作成しています。
 */
public interface DialogListener {

    /**
     * ダイアログイベント結果
     *
     * @param requestCode ダイアログ呼び出し時に指定したリクエストコード。
     * @param resultCode  ダイアログに応じた結果コード。
     *                    ボタンの場合は通常、下記の値が返ります。
     *                    <li>{@link android.content.DialogInterface#BUTTON_POSITIVE}</li>
     *                    <li>{@link android.content.DialogInterface#BUTTON_NEGATIVE}</li>
     *                    <li>{@link android.content.DialogInterface#BUTTON_NEUTRAL}</li>
     * @param data        ダイアログの結果パラメータ（データ無し = null）
     */
    void onDialogResult(int requestCode, int resultCode, Intent data);

}