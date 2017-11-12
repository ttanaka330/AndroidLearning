package jp.ttanaka330.androidlearning.library.realm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import jp.ttanaka330.androidlearning.BR;

public class RealmUserEditViewModel extends BaseObservable {

    private Callback callback;

    private String name;
    private String age;
    private String url;

    public interface Callback {
        void onNameChanged(String name);
    }

    public RealmUserEditViewModel() {
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (callback != null) {
            callback.onNameChanged(name);
        }
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

}
