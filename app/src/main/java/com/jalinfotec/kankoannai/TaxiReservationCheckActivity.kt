package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_taxi_reservation_check.*

class TaxiReservationCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation_check)

        //予約登録画面からの情報を表示
        day.text = intent.getStringExtra("DAY")
        time.text = intent.getStringExtra("TIME")
        adlt.text = intent.getStringExtra("ADLT")
        chld.text = intent.getStringExtra("CHLD")
        disp.text = intent.getStringExtra("DISP")
        taxi.text = intent.getStringExtra("TAXI")
        dest.text = intent.getStringExtra("DEST")
        name.text = intent.getStringExtra("NAME")
        kana.text = intent.getStringExtra("KANA")
        phone.text = intent.getStringExtra("PHONE")
        mail.text = intent.getStringExtra("MAIL")
        comment.text = intent.getStringExtra("COMMENT")
    }
}
