package jp.ttanaka330.androidlearning.presentation.realm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.annotations.NonNull;
import jp.ttanaka330.androidlearning.R;

/**
 * {@link io.realm.Realm} に保存されたデータの表示、および、編集を行う {@link android.app.Activity} です。
 *
 * @see <a href="https://realm.io/jp/">Realm</a>
 */
public class RealmActivity extends AppCompatActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, RealmActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        setSupportActionBar(findViewById(R.id.toolbar));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, RealmFragment.newInstance())
                    .commit();
        }
    }
}
