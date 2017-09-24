package jp.ttanaka330.androidlearning.di;

import dagger.Subcomponent;
import jp.ttanaka330.androidlearning.ui.activity.MainActivity;

@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    FragmentComponent plus(FragmentModule module);

}
