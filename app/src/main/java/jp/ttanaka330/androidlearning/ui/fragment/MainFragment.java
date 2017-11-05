package jp.ttanaka330.androidlearning.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jp.ttanaka330.androidlearning.databinding.FragmentMainBinding;
import jp.ttanaka330.androidlearning.ui.activity.RealmActivity;
import jp.ttanaka330.androidlearning.ui.activity.RetrofitActivity;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

public class MainFragment extends BaseFragment {

    private FragmentMainBinding mBinding;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * {@link MainFragment} のインスタンスを生成します。
     *
     * @return {@link MainFragment} の新規インスタンス
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        initFragmentList();
        return mBinding.getRoot();
    }

    private void initFragmentList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(RealmActivity.createIntent(getContext())));
        list.add(new Item(RetrofitActivity.createIntent(getContext())));

        RecyclerSimpleAdapter adapter = new RecyclerSimpleAdapter<Item>(list) {
            @Override
            protected void onItemClicked(int position, Item item) {
                startActivity(item.getIntent());
            }
        };

        Context context = getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        mBinding.listView.setAdapter(adapter);
        mBinding.listView.setLayoutManager(layoutManager);
        mBinding.listView.addItemDecoration(decoration);
    }

    private class Item {
        private final Intent mIntent;

        Item(@NonNull  Intent intent) {
            mIntent = intent;
        }

        @NonNull
        Intent getIntent() {
            return mIntent;
        }

        @Override
        public String toString() {
            assert mIntent.getComponent() != null;
            int pos = mIntent.getComponent().getClassName().lastIndexOf(".") + 1;
            return mIntent.getComponent().getShortClassName().substring(pos);
        }
    }
}
