package jp.ttanaka330.androidlearning.service.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @see <a href="https://openweathermap.org/forecast5">OpenWeatherMap - 5 day weather forecast</a>
 */
public class WeatherResponse {

    public String cod;
    public double message;
    public int cnt;
    @SerializedName("list")
    public List<Data> lists;

    public class Data {
        public int dt;
        public Main main;
        @SerializedName("weather")
        public List<Weather> weathers;
        public Clouds clouds;
        public Wind wind;
        public Rain rain;
        public Sys sys;
        @SerializedName("dt_txt")
        public String dtTxt;
    }

    public class Main {
        public double temp;
        @SerializedName("temp_min")
        public double tempMin;
        @SerializedName("temp_max")
        public double tempMax;
        public double pressure;
        @SerializedName("sea_level")
        public double seaLevel;
        @SerializedName("grnd_level")
        public double grndLevel;
        public int humidity;
        @SerializedName("temp_kf")
        public double tempKf;
    }

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Clouds {
        public int all;
    }

    public class Wind {
        public double speed;
        public double deg;
    }

    public class Rain {
        @SerializedName("3h")
        public double d3h;
    }

    public class Sys {
        public String pod;
    }

}