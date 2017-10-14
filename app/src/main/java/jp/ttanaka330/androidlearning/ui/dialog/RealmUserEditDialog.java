package jp.ttanaka330.androidlearning.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.model.User;

/**
 * {@link User} データ編集ダイアログ。
 * 編集結果は {@link DialogListener#onDialogResult(int, int, Intent)} にて返されます。
 */
public class RealmUserEditDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String DATA_REQUEST_CODE = "request_code";
    public static final String DATA_NAME = "name";
    public static final String DATA_AGE = "age";
    public static final String DATA_URL = "url";

    public static final int RESULT_CANCEL = 0;
    public static final int RESULT_REGISTER = 1;
    public static final int RESULT_UPDATE = 2;
    public static final int RESULT_DELETE = 9;

    private DialogListener mListener;
    private EditText mEditName;
    private EditText mEditAge;
    private EditText mEditUrl;

    /**
     * ダイアログ生成
     *
     * @param requestCode リクエストコード
     * @param user        ユーザー情報
     * @return ダイアログのインスタンス
     */
    public static RealmUserEditDialog createDialog(int requestCode, @Nullable User user) {
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
        View view = inflater.inflate(R.layout.dialog_realm_user, null);
        mEditName = view.findViewById(R.id.edit_name);
        mEditAge = view.findViewById(R.id.edit_age);
        mEditUrl = view.findViewById(R.id.edit_url);

        if (getArguments().containsKey(DATA_NAME)) {
            mEditName.setText(getArguments().getString(DATA_NAME));
            mEditAge.setText(getArguments().getString(DATA_AGE));
            mEditUrl.setText(getArguments().getString(DATA_URL));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setView(view);
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
        setCustomizeEvent((AlertDialog) getDialog());
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

    private void setCustomizeEvent(AlertDialog dialog) {
        final Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setEnabled(!TextUtils.isEmpty(mEditName.getText()));

        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                positive.setEnabled(!TextUtils.isEmpty(s));
            }
        });
    }

    private boolean isRegister() {
        return !getArguments().containsKey(DATA_NAME);
    }

    private void result(int resultCode) {
        Intent data = null;
        if (resultCode == RESULT_REGISTER || resultCode == RESULT_UPDATE) {
            data = new Intent();
            data.putExtra(DATA_NAME, convertResultString(mEditName));
            data.putExtra(DATA_AGE, convertResultString(mEditAge));
            data.putExtra(DATA_URL, convertResultString(mEditUrl));
        }
        mListener.onDialogResult(getArguments().getInt(DATA_REQUEST_CODE), resultCode, data);
    }

    private String convertResultString(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            return null;
        }
        return editText.getText().toString();
    }
}
