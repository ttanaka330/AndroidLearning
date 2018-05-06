package jp.ttanaka330.androidlearning.presentation.retrofit;

import java.util.Locale;

public class RetrofitWeatherViewModel {

    private String datetime;
    private String weather;
    private String icon;
    private Double celsius;

    RetrofitWeatherViewModel(String datetime, String weather, String icon, Double celsius) {
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

    private String convertString(Double value) {
        return value == null ? null : String.format(Locale.ENGLISH, "%1$.2f℃", value);
    }
}
