package com.jalinfotec.kankoannai

import android.content.Context
import android.os.Handler
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.View

class BaseEditText : AppCompatEditText {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setOnClickListener(listener: View.OnClickListener?) {
        super.setOnClickListener { view ->
            view.isEnabled = false
            Handler().postDelayed({ view.isEnabled = true }, 1500L)
            listener!!.onClick(view)
        }
    }
}