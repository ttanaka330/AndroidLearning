package jp.ttanaka330.androidlearning.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import jp.ttanaka330.androidlearning.MainApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}
