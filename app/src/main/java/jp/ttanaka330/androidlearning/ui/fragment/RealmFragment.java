package jp.ttanaka330.androidlearning.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.model.User;
import jp.ttanaka330.androidlearning.repository.RealmDatabase;
import jp.ttanaka330.androidlearning.ui.dialog.DialogListener;
import jp.ttanaka330.androidlearning.ui.dialog.RealmUserEditDialog;
import jp.ttanaka330.androidlearning.ui.view.RecyclerSimpleAdapter;

public class RealmFragment extends BaseFragment implements DialogListener {

    @Inject
    RealmDatabase mDatabase;

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
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public void onDetach() {
        super.onDetach();
        mDatabase.close();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDialogResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RealmUserEditDialog.RESULT_CANCEL:
                return;
            case RealmUserEditDialog.RESULT_REGISTER:
                mDatabase.execute(realm -> {
                    User user = realm.createObject(User.class, User.createKey());
                    updateUserData(user, data);
                    mAdapter.add(user);
                });
                break;
            case RealmUserEditDialog.RESULT_UPDATE:
                mDatabase.execute(realm -> updateUserData(mAdapter.getItem(requestCode), data));
                mAdapter.notifyItemChanged(requestCode);
                break;
            case RealmUserEditDialog.RESULT_DELETE:
                User user = mAdapter.getItem(requestCode);
                mDatabase.execute(realm -> user.deleteFromRealm());
                mAdapter.removeItem(requestCode);
                break;
        }
    }

    @OnClick(R.id.fab)
    void onRegisterClicked() {
        showUserEditDialog(-1, null);
    }

    private void initUserList() {
        Context context = getContext();
        List<User> users = mDatabase.findAll(User.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        mAdapter = new RecyclerSimpleAdapter<User>(users) {
            @Override
            protected void onItemClicked(int position, User item) {
                showUserEditDialog(position, item);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(layoutManager);
        mListView.addItemDecoration(decoration);
    }

    private User updateUserData(@NonNull User user, @NonNull Intent data) {
        user.setName(data.getStringExtra(RealmUserEditDialog.DATA_NAME));
        String age = data.getStringExtra(RealmUserEditDialog.DATA_AGE);
        user.setAge((TextUtils.isEmpty(age)) ? null : Integer.parseInt(age));
        user.setUrl(data.getStringExtra(RealmUserEditDialog.DATA_URL));
        return user;
    }

    private void showUserEditDialog(int requestCode, @Nullable User user) {
        RealmUserEditDialog.createDialog(requestCode, user)
                .show(getChildFragmentManager(), null);
    }
}
