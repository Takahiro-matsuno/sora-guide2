package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_taxi_reservation_comp.*
import org.jetbrains.anko.startActivity

class MyTaxiCancelCompActivity : AppCompatActivity() {

    companion object {
        const val resultKey = "RESULT_TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi_cancel_comp)

        toReg_button.setOnClickListener {
            // タクシー予約画面へ遷移
            startActivity<TaxiReservationActivity>(Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi))
            this.finish()
        }
        toCheck_button.setOnClickListener {
            // Myタクシー確認画面へ遷移
            startActivity<MyTaxiActivity>(Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi))
            this.finish()
        }
        // 予約キャンセルの結果を設定する
        result_text.text =
                if (savedInstanceState == null) intent.getStringExtra(resultKey)
                else savedInstanceState.getString(resultKey)
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(resultKey, result_text.text.toString())
    }
}
