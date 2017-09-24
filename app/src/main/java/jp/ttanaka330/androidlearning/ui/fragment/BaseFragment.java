package jp.ttanaka330.androidlearning.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import jp.ttanaka330.androidlearning.di.FragmentComponent;
import jp.ttanaka330.androidlearning.di.FragmentModule;
import jp.ttanaka330.androidlearning.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {

    private FragmentComponent mComponent;

    public FragmentComponent getComponent() {
        if (mComponent == null) {
            Activity activity = getActivity();
            if (activity instanceof BaseActivity) {
                mComponent = ((BaseActivity) activity).getComponent()
                        .plus(new FragmentModule(this));
            }
        }
        return mComponent;
    }
}
