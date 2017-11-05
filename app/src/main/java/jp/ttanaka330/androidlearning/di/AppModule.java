package jp.ttanaka330.androidlearning.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.ttanaka330.androidlearning.api.repository.RealmDatabase;

@Module
class AppModule {

    @Singleton
    @Provides
    RealmDatabase provideDatabase() {
        return new RealmDatabase();
    }

}
