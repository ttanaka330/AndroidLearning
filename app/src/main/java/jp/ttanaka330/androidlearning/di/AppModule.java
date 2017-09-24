package jp.ttanaka330.androidlearning.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.ttanaka330.androidlearning.repository.RealmDatabase;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Application application) {
        mContext = application;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    RealmDatabase provideDatabase() {
        return new RealmDatabase();
    }

}
