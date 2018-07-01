package jp.ttanaka330.androidlearning.presentation.butterknife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import jp.ttanaka330.androidlearning.R;

/**
 * @see <a href="http://jakewharton.github.io/butterknife/">Butter Knife</a>
 */
public class ButterKnifeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, ButterKnifeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, ButterKnifeFragment.newInstance())
                    .commit();
        }
    }

}