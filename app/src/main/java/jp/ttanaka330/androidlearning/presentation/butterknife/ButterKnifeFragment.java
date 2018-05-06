package jp.ttanaka330.androidlearning.presentation.butterknife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import jp.ttanaka330.androidlearning.R;

public class ButterKnifeFragment extends Fragment {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.input_layout)
    TextInputLayout mInputLayout;
    @BindView(R.id.edit)
    TextInputEditText mEdit;
    @BindView(R.id.spinner)
    Spinner mSpinner;

    private Unbinder mUnbinder;
    private boolean mInitialized;

    public static ButterKnifeFragment newInstance() {
        return new ButterKnifeFragment();
    }

    public ButterKnifeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butterknife, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.button)
    void onButtonClick(/*View view*/) {
        showSnackBar(mButton.getText().toString());
    }

    @OnEditorAction(R.id.edit)
    boolean onEditorActionEditText(TextView view, int actionId/*, KeyEvent event*/) {
        if (actionId == KeyEvent.ACTION_DOWN) {
            showSnackBar(view.getText().toString());
            return false;
        }
        return true;
    }

    @OnTextChanged(R.id.edit)
    void onTextChangedEditText(/*CharSequence s, int start, int before, int count*/) {
        final String text = mEdit.getText().toString();
        if (text.matches("^[0-9a-zA-Z]*$")) {
            mInputLayout.setError(null);
            mInputLayout.setErrorEnabled(false);
        } else {
            mInputLayout.setError("error");
            mInputLayout.setErrorEnabled(true);
        }
    }

    @OnItemSelected(R.id.spinner)
    void onItemSelectedSpinner(AdapterView<?> parent/*, View view, int position, long id*/) {
        if (!mInitialized) {
            // 初期表示時に呼ばれるための対応
            mInitialized = true;
            return;
        }
        showSnackBar(parent.getSelectedItem().toString());
    }

    private void showSnackBar(String text) {
        Snackbar.make(mLayout, text, Snackbar.LENGTH_SHORT).show();
    }
}
