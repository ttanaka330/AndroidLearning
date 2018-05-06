package jp.ttanaka330.androidlearning.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.ttanaka330.androidlearning.di.scope.ActivityScope
import jp.ttanaka330.androidlearning.di.scope.FragmentScope
import jp.ttanaka330.androidlearning.presentation.retrofit.RetrofitActivity
import jp.ttanaka330.androidlearning.presentation.retrofit.RetrofitFragment

@Module
internal abstract class ActivityModule {

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
