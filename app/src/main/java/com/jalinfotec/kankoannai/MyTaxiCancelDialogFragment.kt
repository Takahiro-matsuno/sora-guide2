package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.os.Bundle


class MyTaxiCancelDialogFragment : DialogFragment() {
    var title = ""
    var msg = ""
    var positiveText = ""
    var negativeText = ""
    var onPositiveClickListener: DialogInterface.OnClickListener? = null
    var onNegativeClickListener: DialogInterface.OnClickListener? = DialogInterface.OnClickListener { _, _ -> }

    @SuppressLint("PrivateResource")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.Base_Theme_AppCompat_Light_Dialog_Alert)
        builder.setTitle(title)
            .setMessage(msg)
            .setPositiveButton(positiveText, onPositiveClickListener)
            .setNegativeButton(negativeText, onNegativeClickListener)
        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }


}