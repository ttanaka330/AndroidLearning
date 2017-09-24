package jp.ttanaka330.androidlearning.di;

import dagger.Subcomponent;
import jp.ttanaka330.androidlearning.ui.fragment.MainFragment;

@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment fragment);
}
