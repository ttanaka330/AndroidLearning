package jp.ttanaka330.androidlearning.presentation.realm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import jp.ttanaka330.androidlearning.databinding.FragmentRealmBinding;
import jp.ttanaka330.androidlearning.common.dialog.DialogListener;
import jp.ttanaka330.androidlearning.common.fragment.BaseFragment;
import jp.ttanaka330.androidlearning.common.view.RecyclerSimpleAdapter;

public class RealmFragment extends BaseFragment implements DialogListener, RealmViewModel.Callback {

    @Inject
    RealmDatabase mDatabase;
    @Inject
    RealmViewModel mViewModel;

    private FragmentRealmBinding mBinding;
    private RecyclerSimpleAdapter<User> mAdapter;

    /**
     * {@link RealmFragment} のインスタンスを生成します。
     *
     * @return {@link RealmFragment} の新規インスタンス
     */
    public static RealmFragment newInstance() {
        return new RealmFragment();
    }

    public RealmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentRealmBinding.inflate(inflater, container, false);
        mBinding.setModel(mViewModel);
        initUserList();
        return mBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDatabase.close();
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

    @Override
    public void onRegisterClicked() {
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
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.setLayoutManager(layoutManager);
        mBinding.listView.addItemDecoration(decoration);
    }

    private User updateUserData(@NonNull User user, @NonNull Intent data) {
        user.setName(data.getStringExtra(RealmUserEditDialog.DATA_NAME));
        String age = data.getStringExtra(RealmUserEditDialog.DATA_AGE);
        user.setAge((TextUtils.isEmpty(age)) ? null : Integer.parseInt(age));
        user.setUrl(data.getStringExtra(RealmUserEditDialog.DATA_URL));
        return user;
    }

    private void showUserEditDialog(int requestCode, @Nullable User user) {
        RealmUserEditDialog.newInstance(requestCode, user)
                .show(getChildFragmentManager(), null);
    }

}
