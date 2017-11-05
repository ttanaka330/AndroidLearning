package jp.ttanaka330.androidlearning.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jp.ttanaka330.androidlearning.di.scope.ActivityScope;
import jp.ttanaka330.androidlearning.di.scope.FragmentScope;
import jp.ttanaka330.androidlearning.ui.activity.MainActivity;
import jp.ttanaka330.androidlearning.ui.activity.RealmActivity;
import jp.ttanaka330.androidlearning.ui.activity.RetrofitActivity;
import jp.ttanaka330.androidlearning.ui.fragment.MainFragment;
import jp.ttanaka330.androidlearning.ui.fragment.RealmFragment;
import jp.ttanaka330.androidlearning.ui.fragment.RetrofitFragment;

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();

    @Module
    abstract class MainActivityModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = FragmentModule.class)
        abstract MainFragment contributeMainFragment();
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = RealmActivityModule.class)
    abstract RealmActivity contributeRealmActivity();

    @Module
    abstract class RealmActivityModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = FragmentModule.class)
        abstract RealmFragment contributeRealmFragment();
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = RetrofitActivityModule.class)
    abstract RetrofitActivity contributeRetrofitActivity();

    @Module
    abstract class RetrofitActivityModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = FragmentModule.class)
        abstract RetrofitFragment contributeRetrofitFragment();
    }
}
