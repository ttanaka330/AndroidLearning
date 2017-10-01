package jp.ttanaka330.androidlearning.ui.fragment;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
