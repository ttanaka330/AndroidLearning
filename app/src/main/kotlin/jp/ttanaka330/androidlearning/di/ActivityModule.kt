package jp.ttanaka330.androidlearning.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.ttanaka330.androidlearning.di.scope.ActivityScope
import jp.ttanaka330.androidlearning.di.scope.FragmentScope
import jp.ttanaka330.androidlearning.presentation.butterknife.ButterKnifeActivity
import jp.ttanaka330.androidlearning.presentation.butterknife.ButterKnifeFragment
import jp.ttanaka330.androidlearning.presentation.realm.RealmActivity
import jp.ttanaka330.androidlearning.presentation.retrofit.RetrofitActivity
import jp.ttanaka330.androidlearning.presentation.realm.RealmFragment
import jp.ttanaka330.androidlearning.presentation.retrofit.RetrofitFragment

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(ButterKnifeModule::class)])
    internal abstract fun contributeButterKnifeActivity(): ButterKnifeActivity

    @Module
    internal abstract inner class ButterKnifeModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = [(FragmentModule::class)])
        internal abstract fun contributeButterKnifeFragment(): ButterKnifeFragment
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = [(RealmActivityModule::class)])
    internal abstract fun contributeRealmActivity(): RealmActivity

    @Module
    internal abstract inner class RealmActivityModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = [(FragmentModule::class)])
        internal abstract fun contributeRealmFragment(): RealmFragment
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = [(RetrofitActivityModule::class)])
    internal abstract fun contributeRetrofitActivity(): RetrofitActivity

    @Module
    internal abstract inner class RetrofitActivityModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = [(FragmentModule::class)])
        internal abstract fun contributeRetrofitFragment(): RetrofitFragment
    }
}
