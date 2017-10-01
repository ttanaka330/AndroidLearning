package jp.ttanaka330.androidlearning.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.model.User;
import jp.ttanaka330.androidlearning.repository.RealmDatabase;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

public class RealmFragment extends BaseFragment {

    @Inject
    RealmDatabase mDatabase;

    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_age)
    EditText mEditAge;
    @BindView(R.id.edit_url)
    EditText mEditUrl;
    @BindView(R.id.list_view)
    RecyclerView mListView;

    private RecyclerSimpleAdapter<User> mAdapter;
    private Unbinder mUnbinder;

    public static RealmFragment newInstance() {
        return new RealmFragment();
    }

    public RealmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realm, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initUserList();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDatabase.close();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.button_entry)
    void onEntryClicked() {
        User user = new User();
        user.setName(mEditName.getText().toString());
        if (!TextUtils.isEmpty(mEditAge.getText())) {
            user.setAge(Integer.parseInt(mEditAge.getText().toString()));
        }
        user.setUrl(mEditUrl.getText().toString());
        mDatabase.add(user);

        mAdapter.add(user);
        clear();
        mEditName.requestFocus();
    }

    private void initUserList() {
        List<User> users = mDatabase.findAll(User.class);
        mAdapter = new RecyclerSimpleAdapter<>(users);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(mAdapter);
    }

    private void clear() {
        mEditName.setText(null);
        mEditAge.setText(null);
        mEditUrl.setText(null);
    }

}
