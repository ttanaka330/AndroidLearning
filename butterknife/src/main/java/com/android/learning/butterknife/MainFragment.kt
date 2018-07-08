package com.android.learning.butterknife

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnEditorAction
import butterknife.OnItemSelected
import butterknife.OnTextChanged
import butterknife.Unbinder

class MainFragment : Fragment() {

    @BindView(R.id.input_layout)
    lateinit var inputLayout: TextInputLayout
    @BindView(R.id.input_text)
    lateinit var inputText: TextInputEditText
    @BindView(R.id.spinner)
    lateinit var spinner: Spinner
    @BindView(R.id.button)
    lateinit var button: Button

    private lateinit var unbinder: Unbinder
    private var initialized: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    @OnClick(R.id.button)
    internal fun onButtonClick(/*View view*/) {
        showSnackBar(button.text.toString())
    }

    @OnEditorAction(R.id.input_text)
    internal fun onEditorActionEditText(
        view: TextView,
        actionId: Int
        /*, KeyEvent event*/
    ): Boolean {
        if (actionId == KeyEvent.ACTION_DOWN) {
            val text = view.text.toString()
            if (text.isNotBlank()) showSnackBar(text)
            return false
        }
        return true
    }

    @OnTextChanged(R.id.input_text)
    internal fun onTextChangedEditText(/*CharSequence s, int start, int before, int count*/) {
        val text = inputText.text.toString()
        if (text.matches("^[0-9a-zA-Z]*$".toRegex())) {
            inputLayout.error = null
            inputLayout.isErrorEnabled = false
        } else {
            inputLayout.error = getText(R.string.input_error)
            inputLayout.isErrorEnabled = true
        }
    }

    @OnItemSelected(R.id.spinner)
    internal fun onItemSelectedSpinner(parent: AdapterView<*>/*, view: View, pos: Int, id: Long*/) {
        // 初期表示時に呼ばれるための対応
        if (!initialized) {
            initialized = true
        } else {
            showSnackBar(parent.selectedItem.toString())
        }
    }

    private fun showSnackBar(text: String) {
        view?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
