package jp.ttanaka330.androidlearning.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
internal class BuildTypeAppModule {

    @Singleton
    @Provides
    @IntoSet
    fun provideStethoLogger(): Interceptor = StethoInterceptor()

    @Singleton
    @Provides
    @IntoSet
    fun provideHttpLogger(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
