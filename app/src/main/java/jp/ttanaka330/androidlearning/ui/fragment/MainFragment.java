package jp.ttanaka330.androidlearning.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.FragmentMainBinding;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

/**
 * アプリ起動時に表示される MainFragment。
 * 各画面へのポータルとなる。
 */
public class MainFragment extends BaseFragment {

    private FragmentMainBinding mBinding;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * MainFragment インスタンス生成
     *
     * @return MainFragment の新規インスタンス
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        initFragmentList();
        return mBinding.getRoot();
    }

    private void initFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(RealmFragment.newInstance());
        list.add(RetrofitFragment.newInstance());

        RecyclerSimpleAdapter adapter = new RecyclerSimpleAdapter<BaseFragment>(list) {
            @Override
            protected void onItemClicked(int position, BaseFragment item) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_view, item)
                        .addToBackStack(item.getClass().getSimpleName())
                        .commit();
            }
        };

        Context context = getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        mBinding.listView.setAdapter(adapter);
        mBinding.listView.setLayoutManager(layoutManager);
        mBinding.listView.addItemDecoration(decoration);
    }
}
