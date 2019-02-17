package com.github.ttanaka330.learning.todo.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class ConfirmMessageDialog : DialogFragment() {

    companion object {
        private const val ARG_MESSAGE_ID = "MESSAGE_ID"

        fun newInstance(@StringRes messageResId: Int) =
            ConfirmMessageDialog().apply {
                arguments = Bundle().apply { putInt(ARG_MESSAGE_ID, messageResId) }
            }
    }

    interface ConfirmDialogListener {
        fun onDialogResult(which: Int)
    }

    private var listener: ConfirmDialogListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when {
            targetFragment is ConfirmDialogListener ->
                listener = (targetFragment as ConfirmDialogListener)
            parentFragment is ConfirmDialogListener ->
                listener = (parentFragment as ConfirmDialogListener)
            activity is ConfirmDialogListener ->
                listener = (activity as ConfirmDialogListener)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState)
        }
        val builder = AlertDialog.Builder(activity!!)
        arguments?.let { builder.setMessage(it.getInt(ARG_MESSAGE_ID)) }
        return builder
            .setPositiveButton(android.R.string.ok) { _, which ->
                listener?.onDialogResult(which)
            }
            .setNegativeButton(android.R.string.cancel) { _, which ->
                listener?.onDialogResult(which)
            }
            .create()
    }
}