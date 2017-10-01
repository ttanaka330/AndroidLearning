package jp.ttanaka330.androidlearning.di;

import dagger.Subcomponent;
import jp.ttanaka330.androidlearning.ui.fragment.MainFragment;
import jp.ttanaka330.androidlearning.ui.fragment.RealmFragment;

@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment fragment);

    void inject(RealmFragment fragment);
}
