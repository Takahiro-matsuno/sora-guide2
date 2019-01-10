package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_taxi_detail.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class MyTaxiDetailActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat", "PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi_detail)

        //予約登録画面からの情報を表示
        bookingId.text = intent.getStringExtra("ID")

        //予約番号をキーに予約情報を検索
        val bookingDetailInfo = TaxiStub().getTaxiBookingInfo(bookingId.text.toString())

        //予約情報を得られた場合、画面の各項目に値を設定
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
        } else {
            //TODO 予約情報の取得に失敗した場合の処理
        }

        //変更ボタン押下時の動作
        changeButton.setOnClickListener {
            //TODO 予約変更画面作成後に更新
        }

        //取消ボタン押下時の動作
        cancelButton.setOnClickListener {
            /*
            AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Dialog_Alert).apply {
                setTitle("予約取消確認")
                setMessage("タクシー予約を取り消します。")

                setPositiveButton("はい") { _, _ ->
                    if (TaxiStub().cancelBookingAction(bookingId.text.toString())) {
                        startActivity<MyTaxiCancelCompActivity>()
                    } else {
                        //TODO 予約取り消しに失敗した場合のエラー表示
                    }
                    setNegativeButton()
                }

                setNegativeButton("いいえ", null)
                show()
            }*/
            val dialog = MyTaxiCancelDialogFragment()
            dialog.title = "予約取消確認"
            dialog.msg = "タクシー予約を取り消します。"
            dialog.positiveText = "はい"
            dialog.negativeText = "いいえ"
            dialog.onPositiveClickListener = DialogInterface.OnClickListener { _, _ ->
                if (TaxiStub().cancelBookingAction(bookingId.text.toString())) {
                    startActivity<MyTaxiCancelCompActivity>()
                } else {
                    //TODO 予約取り消しに失敗した場合のエラー表示
                }
            }
            dialog.show(supportFragmentManager, "tag")
        }
    }

    //戻るボタン制御
    override fun onBackPressed() {
        //予約確認画面へ遷移
        startActivity<MyTaxiActivity>()
        finish()
    }
}