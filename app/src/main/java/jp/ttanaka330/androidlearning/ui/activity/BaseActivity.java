package jp.ttanaka330.androidlearning.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import jp.ttanaka330.androidlearning.MainApplication;
import jp.ttanaka330.androidlearning.di.ActivityComponent;
import jp.ttanaka330.androidlearning.di.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mComponent;

    @NonNull
    public ActivityComponent getComponent() {
        if (mComponent == null) {
            mComponent = ((MainApplication)getApplication())
                    .getComponent().plus(new ActivityModule(this));
        }
        return mComponent;
    }

}
