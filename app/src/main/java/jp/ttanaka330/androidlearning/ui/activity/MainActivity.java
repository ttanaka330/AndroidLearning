package jp.ttanaka330.androidlearning.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.ActivityMainBinding;
import jp.ttanaka330.androidlearning.ui.fragment.MainFragment;

/**
 * アプリ起動時に表示される {@link android.app.Activity} です。
 * 各 {@link android.app.Activity}  へのポータルを担います。
 *
 * @see MainFragment
 */
public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);
        if (savedInstanceState == null) {
            replaceFragment(R.id.content_view, MainFragment.newInstance());
        }
    }

}
