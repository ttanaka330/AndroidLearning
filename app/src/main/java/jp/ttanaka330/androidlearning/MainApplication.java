package jp.ttanaka330.androidlearning;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.ttanaka330.androidlearning.di.DaggerAppComponent;

public class MainApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(1L)
                .build());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
