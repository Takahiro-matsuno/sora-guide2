package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_taxi.*
import org.jetbrains.anko.startActivity

class MyTaxiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi)

        reg_tab.setOnClickListener {
            // タクシー予約画面へ遷移
            startActivity<TaxiReservationActivity>()
            this.finish()
        }
    }
}
