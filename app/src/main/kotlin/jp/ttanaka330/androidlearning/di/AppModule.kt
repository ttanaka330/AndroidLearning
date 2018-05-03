package jp.ttanaka330.androidlearning.di

import dagger.Module
import dagger.Provides
import jp.ttanaka330.androidlearning.presentation.realm.RealmDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [BuildTypeAppModule::class])
internal class AppModule {

    @Singleton
    @Provides
    fun provideRealm(): RealmDatabase = RealmDatabase()

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
            OkHttpClient.Builder().apply {
                loggingInterceptors.forEach { addInterceptor(it) }
            }.build()
}
