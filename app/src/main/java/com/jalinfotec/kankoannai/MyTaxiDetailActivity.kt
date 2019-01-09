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

        if (bookingDetailInfo != null) {
            val df = SimpleDateFormat("yyyy/MM/dd")
            val tf = SimpleDateFormat("hh:mm")
            day.text = df.format(bookingDetailInfo.rideOnDate)
            time.text = tf.format(bookingDetailInfo.rideOnDate)
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

        changeButton.setOnClickListener{
            //TODO 予約変更画面作成後に更新
        }

        cancelButton.setOnClickListener {
            if(TaxiStub().CancelBookingAction(bookingId.text.toString())){
                startActivity<MyTaxiCancelCompActivity>()
            }
            else{
                //TODO 予約取り消しに失敗した場合のエラー表示
            }
        }
    }

    //戻るボタン制御
    override fun onBackPressed() {
        //予約確認画面へ遷移
        startActivity<MyTaxiActivity>()
        finish()
    }
}