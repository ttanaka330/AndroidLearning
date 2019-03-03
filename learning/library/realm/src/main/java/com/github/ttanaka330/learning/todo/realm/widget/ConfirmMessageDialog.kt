package com.github.ttanaka330.learning.todo.realm.widget

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmMessageDialog : DialogFragment() {

    companion object {
        private const val ARG_MESSAGE_ID = "MESSAGE_ID"

        fun newInstance(@StringRes messageResId: Int) =
            ConfirmMessageDialog().apply {
                arguments = Bundle().apply { putInt(ARG_MESSAGE_ID, messageResId) }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState)
        }
        val listener = DialogInterface.OnClickListener { _, which ->
            targetFragment?.onActivityResult(targetRequestCode, which, null)
        }
        val builder = AlertDialog.Builder(activity!!)
        arguments?.let { builder.setMessage(it.getInt(ARG_MESSAGE_ID)) }
        return builder
            .setPositiveButton(android.R.string.ok, listener)
            .setNegativeButton(android.R.string.cancel, listener)
            .create()
    }
}