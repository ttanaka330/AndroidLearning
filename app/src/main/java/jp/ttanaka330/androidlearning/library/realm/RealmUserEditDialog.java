package jp.ttanaka330.androidlearning.library.realm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Button;

import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.DialogRealmUserBinding;
import jp.ttanaka330.androidlearning.common.dialog.DialogListener;

/**
 * {@link User} データ編集を行うダイアログです。
 * 編集結果は {@link DialogListener#onDialogResult(int, int, Intent)} にて返されます。
 */
public class RealmUserEditDialog extends DialogFragment
        implements DialogInterface.OnClickListener, RealmUserEditViewModel.Callback {

    private static final String DATA_REQUEST_CODE = "request_code";
    public static final String DATA_NAME = "name";
    public static final String DATA_AGE = "age";
    public static final String DATA_URL = "url";

    public static final int RESULT_CANCEL = 0;
    public static final int RESULT_REGISTER = 1;
    public static final int RESULT_UPDATE = 2;
    public static final int RESULT_DELETE = 9;

    private DialogListener mListener;
    private DialogRealmUserBinding mBinding;

    /**
     * {@link RealmUserEditDialog} のインスタンスを生成します。
     *
     * @param requestCode リクエストコード
     * @param user        ユーザー情報
     * @return {@link RealmUserEditDialog} インスタンス
     */
    public static RealmUserEditDialog newInstance(int requestCode, @Nullable User user) {
        RealmUserEditDialog dialog = new RealmUserEditDialog();
        Bundle args = new Bundle();
        args.putInt(DATA_REQUEST_CODE, requestCode);
        if (user != null) {
            args.putString(DATA_NAME, user.getName());
            args.putString(DATA_AGE, (user.getAge() == null) ? null : user.getAge().toString());
            args.putString(DATA_URL, user.getUrl());
        }
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_realm_user, null, false);

        // TODO: dagger に DaggerDialogFragment が実装されたら Inject させる
        RealmUserEditViewModel model = new RealmUserEditViewModel();
        model.setCallback(this);
        if (getArguments().containsKey(DATA_NAME)) {
            model.setName(getArguments().getString(DATA_NAME));
            model.setAge(getArguments().getString(DATA_AGE));
            model.setUrl(getArguments().getString(DATA_URL));
        }
        mBinding.setModel(model);
        mBinding.executePendingBindings();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setView(mBinding.getRoot());
        if (isRegister()) {
            builder.setTitle(R.string.realm_user_register)
                    .setPositiveButton(R.string.register, this)
                    .setNegativeButton(R.string.cancel, this);
        } else {
            builder.setTitle(R.string.realm_user_edit)
                    .setPositiveButton(R.string.update, this)
                    .setNegativeButton(R.string.cancel, this)
                    .setNeutralButton(R.string.delete, this);
        }
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment target = getTargetFragment();
        if (target instanceof DialogListener) {
            mListener = (DialogListener) target;
            return;
        }
        Fragment parent = getParentFragment();
        if (parent instanceof DialogListener) {
            mListener = (DialogListener) parent;
            return;
        }
        if (context instanceof DialogListener) {
            mListener = (DialogListener) context;
        } else {
            throw new ClassCastException(context + " must implement RealmUserEditDialog.Listener.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updatePositiveButton(mBinding.getModel().getName());
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                if (isRegister()) {
                    result(RESULT_REGISTER);
                } else {
                    result(RESULT_UPDATE);
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                result(RESULT_CANCEL);
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                result(RESULT_DELETE);
                break;
        }
    }

    @Override
    public void onNameChanged(String name) {
        updatePositiveButton(name);
    }

    private void updatePositiveButton(String name) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            final Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positive.setEnabled(!TextUtils.isEmpty(name));
        }
    }

    private boolean isRegister() {
        return !getArguments().containsKey(DATA_NAME);
    }

    private void result(int resultCode) {
        Intent data = null;
        if (resultCode == RESULT_REGISTER || resultCode == RESULT_UPDATE) {
            RealmUserEditViewModel model = mBinding.getModel();
            data = new Intent();
            data.putExtra(DATA_NAME, convertResult(model.getName()));
            data.putExtra(DATA_AGE, convertResult(model.getAge()));
            data.putExtra(DATA_URL, convertResult(model.getUrl()));
        }
        mListener.onDialogResult(getArguments().getInt(DATA_REQUEST_CODE), resultCode, data);
    }

    private String convertResult(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }
}
