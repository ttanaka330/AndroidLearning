package jp.ttanaka330.androidlearning.ui.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import dagger.android.support.DaggerAppCompatActivity;
import jp.ttanaka330.androidlearning.ui.fragment.BaseFragment;

/**
 * {@link android.app.Activity} を実装する際の基盤となるクラスです。
 * 本アプリでは DI を行うために {@link DaggerAppCompatActivity} を継承しています。
 *
 * @see <a href="https://google.github.io/dagger/">Dagger2</a>
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {

    /**
     * 指定された ID を {@link android.support.v4.app.Fragment} に置換します。
     *
     * @param viewId   置換する {@link android.view.View} のリソース ID
     * @param fragment 置換する {@link BaseFragment} のインスタンス
     */
    protected void replaceFragment(@IdRes int viewId, @NonNull BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(viewId, fragment, fragment.toString())
                .commit();
    }

}
