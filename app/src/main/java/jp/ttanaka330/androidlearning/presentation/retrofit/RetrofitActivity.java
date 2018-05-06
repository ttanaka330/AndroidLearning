package jp.ttanaka330.androidlearning.presentation.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.annotations.NonNull;
import jp.ttanaka330.androidlearning.R;

/**
 * {@link retrofit2.Retrofit} を使用した天気情報表示 {@link android.app.Activity} です。
 *
 * @see <a href="https://google.github.io/dagger/">Dagger</a>
 * @see <a href="http://square.github.io/retrofit/">Retrofit</a>
 * @see <a href="http://square.github.io/picasso/">Picasso</a>
 * @see <a href="https://openweathermap.org/">OpenWeatherMap</a>
 */
public class RetrofitActivity extends DaggerAppCompatActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, RetrofitActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        setSupportActionBar(findViewById(R.id.toolbar));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, RetrofitFragment.newInstance())
                    .commit();
        }
    }
}
