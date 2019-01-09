package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_taxi_detail.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class MyTaxiDetailActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi_detail)

        //予約登録画面からの情報を表示
        bookingId.text = intent.getStringExtra("ID")

        val bookingDetailInfo = TaxiStub().getTaxiBookingInfo(bookingId.text.toString())

        if(bookingDetailInfo != null) {
            val df = SimpleDateFormat("yyyy/MM/dd")
            val tf = SimpleDateFormat("hh:mm")
            day.text = df.format(bookingDetailInfo.rideOnDate)
            time.text =  tf.format(bookingDetailInfo.rideOnDate)
            adlt.text = bookingDetailInfo.adultPassengerNumber.toString()
            chld.text = bookingDetailInfo.childPassengerNumber.toString()
            disp.text = bookingDetailInfo.taxiNumber.toString()
            taxi.text = bookingDetailInfo.companyId
            dest.text = bookingDetailInfo.destination
            name.text = bookingDetailInfo.name
            kana.text = bookingDetailInfo.phonetic
            phone.text = bookingDetailInfo.userPhoneNumber
            mail.text = bookingDetailInfo.mailAddress
            comment.text = bookingDetailInfo.userComment
            carNumber.text = bookingDetailInfo.carNumber
            companyPhoneNumber.text = bookingDetailInfo.companyPhoneNumber
            taxiNotice.text = bookingDetailInfo.taxiNotice
        }
        /*
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

        changeButton.setOnClickListener {
            startActivity<TaxiReservationActivity>(
                Pair("DAY",day.text),Pair("TIME",time.text),
                Pair("ADLT",adlt.text),Pair("CHLD",chld.text),
                Pair("DISP",disp.text),Pair("TAXI",taxi.text),
                Pair("DEST",dest.text),Pair("NAME",name.text),
                Pair("KANA",kana.text),Pair("PHONE",phone.text),
                Pair("MAIL",mail.text),Pair("COMMENT",comment.text)
            )
            this.finish()
        }
        // サーバに予約情報を送信する
        cancelButton.setOnClickListener { postBookingInfo() }
        */

    }

    //戻るボタン制御
    override fun onBackPressed() {
        //予約確認画面へ遷移
        startActivity<MyTaxiActivity>()
        finish()
    }
}