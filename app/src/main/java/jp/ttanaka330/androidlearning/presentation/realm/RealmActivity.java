package jp.ttanaka330.androidlearning.presentation.realm;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.reactivex.annotations.NonNull;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.ActivityRealmBinding;
import jp.ttanaka330.androidlearning.common.activity.BaseActivity;

/**
 * {@link io.realm.Realm} に保存されたデータの表示、および、編集を行う {@link android.app.Activity} です。
 *
 * @see RealmFragment
 * @see <a href="https://realm.io/jp/">Realm</a>
 */
public class RealmActivity extends BaseActivity {

    ActivityRealmBinding mBinding;

    /**
     * {@link RealmActivity} の {@link Intent} を生成します。
     *
     * @param context コンテキスト
     * @return {@link RealmActivity} の {@link Intent}
     */
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, RealmActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_realm);
        setSupportActionBar(mBinding.toolbar);
        if (savedInstanceState == null) {
            replaceFragment(R.id.content_view, RealmFragment.newInstance());
        }
    }
}
