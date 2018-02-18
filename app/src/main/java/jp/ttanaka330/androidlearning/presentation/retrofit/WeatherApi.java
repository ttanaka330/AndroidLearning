package jp.ttanaka330.androidlearning.presentation.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @see <a href="https://openweathermap.org/">OpenWeatherMap</a>
 */
public interface WeatherApi {

    String ENDPOINT = "http://api.openweathermap.org";
    String ICON_URL = "http://openweathermap.org/img/w/";
    String API_KEY = "6505d0e6056d1807d2a13859bf1caf2e";

    @GET("/data/2.5/forecast")
    Observable<WeatherResponse> getWeather(@Query("appid") String appId, @Query("id") String cityId);

}
