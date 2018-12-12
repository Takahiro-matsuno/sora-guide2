package com.jalinfotec.kankoannai

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.widget.NumberPicker
import org.jetbrains.anko.find

class TimePickerDialog:
    BaseCallbackDialog<TimePickerDialog.CallbackListener>() {
    // Bundle格納用キー
    private val hourKey = "HOUR"
    private val maxHourKey = "MAX_HOUR"
    private val minHourKey = "MIN_HOUR"
    private val minuteKey = "MINUTE"
    // picker設定項目
    private var mMinHour = 0                // 予約時刻（最小値）
    private var mMaxHour = 0                // 予約時刻（最大値）
    private var mHour = 0                   // デフォルト表示のための基準時刻
    private var mMinute = 0                 // デフォルト表示のための基準分
    private val interval = 5                // 分間隔
    private val minuteItem: Array<String>   // 分一覧
    // view item
    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker

    init {
        minuteItem = Array(60 / interval) { i ->
            val m = i * interval
            if (m < 10) "0$m" else "$m"
        } // 00~55まで5分刻みで配列に格納される
    }

    interface CallbackListener {
        fun setTime(hour: Int, minute: Int)
    }
    fun newInstance(listener: TimePickerDialog.CallbackListener): TimePickerDialog {
        val dialog = TimePickerDialog()
        dialog.setCallbackListener(listener)
        return dialog
    }
    // 設定項目
    /**
     *  TODO
     *   リアルタイム（今すぐ予約）の場合、画面復帰した際に設定される基準時刻・基準分を値をどうするか.
     *     1. 現状のまま
     *     2. 現在の時刻を再取得して更新する
     */
    fun setParameter(
        minHour: Int,          // 予約時刻（最小値）
        maxHour: Int,          // 予約時刻（最大値）
        hour: Int,             // デフォルト表示のための基準時刻
        minute: Int            // デフォルト表示のための基準分
    ) {
        mMinHour = minHour
        mMaxHour = maxHour
        mHour = hour
        mMinute = minute

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        // 画面復帰時にBundleに保存したデータを取得する
        if (savedInstanceState != null) {
            mMinHour = savedInstanceState.getInt(minHourKey)
            mMaxHour = savedInstanceState.getInt(maxHourKey)
            mHour = savedInstanceState.getInt(hourKey)
            mMinute = savedInstanceState.getInt(minuteKey)
        }

        val builder = AlertDialog.Builder(context)
        builder.setView(createPickerView())
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            /**
             * TODO
             *   時刻のバリデーションチェックはDialog内でも行うか（日付をもっていないため、時刻だけでは過去か未来かの判断ができない）.
             *     1.行う場合、Dialogを呼び出す際にDateクラスを持たす.今日の日付ならバリデーションチェックを行わせる.
             *     2.行わない場合、登録画面で日付＋時刻でチェックを行わせるようにする.
             */
            mListener?.setTime(getHour(), getMinute())
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.setCancelable(true)
        return builder.create()
    }
    private fun createPickerView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_time_picker, null)

        // set Hour Picker
        hourPicker = view.find(R.id.hour_picker)
        hourPicker.minValue = mMinHour
        hourPicker.maxValue = mMaxHour

        // set Minute Picker
        minutePicker = view.find(R.id.minute_picker)
        minutePicker.minValue = 0
        minutePicker.maxValue = minuteItem.size - 1
        minutePicker.displayedValues = minuteItem

        // set Default Value
        /**
         * TODO
         *   日付を持たせる場合は日付も１繰り上げないといけない
         */
        val defaultMinute = mMinute / interval
        if (defaultMinute ==  11) { // defaultMinute == 11(55分~59分)の場合
            // 時間は１繰り上げる
            hourPicker.value = mHour + 1    // 要素数を超える場合は0番目からの値が入る(24=0番目)
            // 分には"00"を設定する
            minutePicker.value = defaultMinute + 1  // 要素数を超える場合は0番目からの値が入る(12=0番目)
        } else {
            hourPicker.value = mHour
            minutePicker.value = defaultMinute + 1
        }

        return view
    }
    // 画面が破棄される際にデータを保存する
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(minHourKey, mMinHour)
        outState.putInt(maxHourKey, mMaxHour)
        outState.putInt(hourKey, mHour)
        outState.putInt(minuteKey, mMinute)
    }
    private fun getHour(): Int {
        return hourPicker.value
    }
    // 分を文字列で取得する
    // １桁の場合は頭に0をつける
    private fun getMinute(): Int {
        return minuteItem[minutePicker.value].toInt()
    }
}
