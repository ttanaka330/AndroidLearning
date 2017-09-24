package jp.ttanaka330.androidlearning.di;

import javax.inject.Singleton;

import dagger.Component;
import jp.ttanaka330.androidlearning.MainApplication;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainApplication application);

    ActivityComponent plus(ActivityModule module);

}
