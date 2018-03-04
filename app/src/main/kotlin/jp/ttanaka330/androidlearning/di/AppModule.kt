package jp.ttanaka330.androidlearning.di

import dagger.Module
import dagger.Provides
import jp.ttanaka330.androidlearning.presentation.realm.RealmDatabase
import javax.inject.Singleton

@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(): RealmDatabase = RealmDatabase()

}
