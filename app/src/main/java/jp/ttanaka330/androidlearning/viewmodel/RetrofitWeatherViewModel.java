package jp.ttanaka330.androidlearning.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class RetrofitWeatherViewModel {

    private String datetime;
    private String weather;
    private String icon;
    private Double celsius;

    public RetrofitWeatherViewModel(String datetime, String weather, String icon, Double celsius) {
        this.datetime = datetime;
        this.weather = weather;
        this.icon = icon;
        this.celsius = celsius;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getWeather() {
        return weather;
    }

    public String getIcon() {
        return icon;
    }

    public String getCelsius() {
        return convertString(celsius);
    }

    @BindingAdapter("app:imageIcon")
    public static void imageIcon(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    private String convertString(Double value) {
        return value == null ? null : String.format(Locale.ENGLISH, "%1$.2f℃", value);
    }
}
