package jp.ttanaka330.androidlearning.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import jp.ttanaka330.androidlearning.BR;

public class SimpleListViewModel extends BaseObservable {

    private String text;

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

}
