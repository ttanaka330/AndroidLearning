package jp.ttanaka330.androidlearning.library.retrofit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.reactivex.annotations.NonNull;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.common.activity.BaseActivity;
import jp.ttanaka330.androidlearning.databinding.ActivityRetrofitBinding;

/**
 * {@link retrofit2.Retrofit} を使用した天気情報表示 {@link android.app.Activity} です。
 *
 * @see RetrofitFragment
 * @see <a href="http://square.github.io/retrofit/">Retrofit</a>
 * @see <a href="https://openweathermap.org/">OpenWeatherMap</a>
 */
public class RetrofitActivity extends BaseActivity {

    ActivityRetrofitBinding mBinding;

    /**
     * {@link RetrofitActivity} の {@link Intent} を生成します。
     *
     * @param context コンテキスト
     * @return {@link RetrofitActivity} の {@link Intent}
     */
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, RetrofitActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit);
        setSupportActionBar(mBinding.toolbar);
        if (savedInstanceState == null) {
            replaceFragment(R.id.content_view, RetrofitFragment.newInstance());
        }
    }
}
