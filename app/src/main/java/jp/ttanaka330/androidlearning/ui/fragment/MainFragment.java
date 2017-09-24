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
import io.reactivex.Observable;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.model.User;
import jp.ttanaka330.androidlearning.repository.RealmDatabase;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

public class MainFragment extends BaseFragment {

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

    private RecyclerSimpleAdapter mAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        loadUserList();
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

    @OnClick(R.id.button_entry)
    void onEntryClicked() {
        User user = new User();
        user.setName(mEditName.getText().toString());
        if (!TextUtils.isEmpty(mEditAge.getText())) {
            user.setAge(Integer.parseInt(mEditAge.getText().toString()));
        }
        user.setUrl(mEditUrl.getText().toString());
        mDatabase.add(user);
        mAdapter.add(formatUserText(user));

        clear();
        mEditName.requestFocus();
    }

    private void loadUserList() {
        List<String> users = Observable.fromIterable(mDatabase.findAll(User.class))
                .map(this::formatUserText)
                .toList()
                .blockingGet();
        mAdapter = new RecyclerSimpleAdapter(users);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(mAdapter);
    }

    private String formatUserText(User user) {
        String text = user.getName();
        if (user.getAge() != null) {
            text += " (" + user.getAge() + ")";
        }
        if (!TextUtils.isEmpty(user.getUrl())) {
            text += " : " + user.getUrl();
        }
        return text;
    }

    private void clear() {
        mEditName.setText(null);
        mEditAge.setText(null);
        mEditUrl.setText(null);
    }


}
