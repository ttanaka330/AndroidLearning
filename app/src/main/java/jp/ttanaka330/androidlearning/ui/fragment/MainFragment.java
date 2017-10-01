package jp.ttanaka330.androidlearning.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

/**
 * アプリ起動時に表示される MainFragment。
 * 各画面へのポータルとなる。
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.list_view)
    RecyclerView mListView;

    private Unbinder mUnbinder;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initFragmentList();
        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    private void initFragmentList() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(RealmFragment.newInstance());
        RecyclerSimpleAdapter adapter = new RecyclerSimpleAdapter<BaseFragment>(list) {
            @Override
            protected void onItemClicked(BaseFragment item) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_view, item)
                        .addToBackStack(item.getClass().getSimpleName())
                        .commit();
            }
        };
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(adapter);
    }
}
