package jp.ttanaka330.androidlearning.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.ActivityMainBinding;
import jp.ttanaka330.androidlearning.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_view, MainFragment.newInstance(), null)
                    .commit();
        }
    }

}
