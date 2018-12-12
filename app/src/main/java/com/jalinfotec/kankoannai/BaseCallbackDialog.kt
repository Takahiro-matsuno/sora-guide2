package com.jalinfotec.kankoannai

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment

abstract class BaseCallbackDialog<Interface>: DialogFragment() {

    enum class ListenerType { ACTIVITY, FRAGMENT, OTHER}
    var mListener: Interface? = null
    private val listenerKey = "LISTENER"
    private var listenerType: ListenerType? = null

    // コールバックリスナーを返す
    fun getCallbackListener(): Interface? = mListener
    // コールバックリスナーを取得する
    fun setCallbackListener(listener: Interface) {
        mListener = listener
        when (mListener) {
            null -> {
                listenerType = null
                setTargetFragment(null, 0)
            }
            is Activity -> {
                listenerType = ListenerType.ACTIVITY
                setTargetFragment(null, 0)
            }
            is Fragment -> {
                listenerType = ListenerType.FRAGMENT
                setTargetFragment(listener as Fragment, 0)
            }
            else -> {
                listenerType = ListenerType.OTHER
                setTargetFragment(null, 0)
            }
        }
        val bundle = Bundle()
        bundle.putSerializable(listenerKey, listenerType)
        arguments = bundle
    }
    // Activity, Fragment再構成時のコネクタ
    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listenerType = this.arguments!!.getSerializable(listenerKey) as ListenerType
        mListener = when (listenerType) {
            ListenerType.ACTIVITY -> activity as Interface
            ListenerType.FRAGMENT -> targetFragment as Interface
            else -> null
        }
    }
}
