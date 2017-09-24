package jp.ttanaka330.androidlearning.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return mFragment.getFragmentManager();
    }

}
