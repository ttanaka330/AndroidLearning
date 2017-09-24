package jp.ttanaka330.androidlearning;

import android.app.Application;
import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.ttanaka330.androidlearning.di.AppComponent;
import jp.ttanaka330.androidlearning.di.AppModule;
import jp.ttanaka330.androidlearning.di.DaggerAppComponent;

public class MainApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);

        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(1L)
                .build());
    }

    @NonNull
    public AppComponent getComponent() {
        return mAppComponent;
    }

}
