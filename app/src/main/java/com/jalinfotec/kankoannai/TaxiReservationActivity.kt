package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_taxi_reservation.*

class TaxiReservationActivity :
    AppCompatActivity(),
    TimePickerDialog.CallbackListener
{

    private val minHour = 0
    private val maxHour = 23
    private var hour = 12
    private var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation)

        time_text.setOnClickListener {
            showTimePickerDialog(minHour, maxHour, hour, minute)
        }
    }

    /**
     * Time PickerDialog
     */
    private fun showTimePickerDialog(minHour: Int, maxHour: Int, hour: Int, minute: Int) {
        val dialog = TimePickerDialog().newInstance(this)
        dialog.setParameter(minHour, maxHour, hour, minute)
        dialog.show(supportFragmentManager, null)
    }
    override fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute

        val hourStr = if (hour < 10) "0$hour" else "$hour"
        val minuteStr = if (minute < 10) "0$minute" else "$minute"

        setTimeText("$hourStr:$minuteStr")
    }
    private fun setTimeText(str: String) = time_text.setText(str)
}
