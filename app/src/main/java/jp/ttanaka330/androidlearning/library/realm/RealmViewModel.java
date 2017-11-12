package jp.ttanaka330.androidlearning.library.realm;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

public class RealmViewModel extends BaseObservable {

    private Callback callback;

    public interface Callback {
        void onRegisterClicked();
    }

    @Inject
    RealmViewModel() {
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public void onRegisterClicked(View view) {
        if (callback != null) {
            callback.onRegisterClicked();
        }
    }

}
