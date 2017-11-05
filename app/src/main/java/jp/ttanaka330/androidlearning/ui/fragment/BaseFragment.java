package jp.ttanaka330.androidlearning.ui.fragment;

import dagger.android.support.DaggerFragment;

/**
 * {@link android.support.v4.app.Fragment} を実装する際の基盤となるクラスです。
 * 本アプリでは DI を行うために {@link DaggerFragment} を継承しています。
 *
 * @see <a href="https://google.github.io/dagger/">Dagger2</a>
 */
public abstract class BaseFragment extends DaggerFragment {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
