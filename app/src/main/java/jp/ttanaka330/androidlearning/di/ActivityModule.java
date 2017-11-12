package jp.ttanaka330.androidlearning.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jp.ttanaka330.androidlearning.di.scope.ActivityScope;
import jp.ttanaka330.androidlearning.di.scope.FragmentScope;
import jp.ttanaka330.androidlearning.library.realm.RealmActivity;
import jp.ttanaka330.androidlearning.library.retrofit.RetrofitActivity;
import jp.ttanaka330.androidlearning.library.realm.RealmFragment;
import jp.ttanaka330.androidlearning.library.retrofit.RetrofitFragment;

@Module
abstract class ActivityModule {

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
