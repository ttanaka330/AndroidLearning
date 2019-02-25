package jp.ttanaka330.androidlearning;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import jp.ttanaka330.androidlearning.di.DaggerAppComponent;

public class MainApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
