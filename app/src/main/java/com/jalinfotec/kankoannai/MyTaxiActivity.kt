package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_my_taxi.*
import org.jetbrains.anko.startActivity

class MyTaxiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi)
        // アニメーションの無効化
        overridePendingTransition(0, 0)



        // 指定したタブを選択状態にする
        tabLayout.getTabAt(1)!!.select()
        // タブのタップイベント
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 選択中のタブがユーザーによって再度選択されたときに呼び出される
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            // タブが選択された状態を終了したときに呼び出される
            override fun onTabSelected(p0: TabLayout.Tab?) {}

            // タブが選択状態になると呼び出される
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                        startActivity<TaxiReservationActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                    1 -> {
                        // TODO 確認画面表示中に予約確認をタップした場合、画面をクリアするか確認する
                        startActivity<MyTaxiActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                }
                finish()
            }
        })

    }
}
