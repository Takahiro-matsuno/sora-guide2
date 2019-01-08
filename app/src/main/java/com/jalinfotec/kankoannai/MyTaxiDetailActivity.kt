package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_my_taxi_detail.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class MyTaxiDetailActivity : AppCompatActivity() {

        private var toast: Toast? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_my_taxi_detail)

            //予約登録画面からの情報を表示
            bookingId.text = intent.getStringExtra("ID")
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
        // 画面がActiveでなくなった時
        override fun onPause() {
            super.onPause()
            toast?.cancel() // 表示中のトーストを消す
        }
        @SuppressLint("SimpleDateFormat")
        private fun postBookingInfo() {
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            val date = sdf.parse("${day.text} ${time.text}")
            // 予約情報クラスを作成
            // TODO 未入力箇所はブランクで送信しているためDBの仕様が決まり次第変更する
            val bookingInfo = TaxiBookingInformation(
                null,
                0,
                Date(date.time),
                //Time(date.time),
                adlt.text.toString().toInt(),
                chld.text.toString().toInt(),
                disp.text.toString().toInt(),
                "",
                dest.text.toString(),
                name.text.toString(),
                kana.text.toString(),
                phone.text.toString(),
                mail.text.toString(),
                comment.text.toString(),
                null,
                null,
                null
            )
            /*
            val endpoint = ""
            val header = null
            val param = null
            val body = Gson().toJson(bookingInfo)
            // TODO API 28から非推奨になったため、いずれ置き換える
            supportLoaderManager.initLoader(0, null, object : LoaderManager.LoaderCallbacks<String> {
                override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<String> {
                    return FuelTaskLoader(
                        this@TaxiReservationCheckActivity,
                        FuelTaskLoader.FuelManager.Method.POST,
                        endpoint,
                        header,
                        param,
                        body)
                }
                override fun onLoadFinished(loader: Loader<String>, result: String?) {
                    supportLoaderManager.destroyLoader(loader.id)
                    if (result == null) {
                    } else {
                        // 送信成功の場合は予約完了画面に遷移する
                        startActivity<TaxiReservationCompActivity>(Pair(TaxiReservationCompActivity.resultKey, result))
                        this@TaxiReservationCheckActivity.finish()
                    }
                }
                override fun onLoaderReset(p0: Loader<String>) {}
            })
            */
            // サーバサイドが未実装のため以下で代用
            /*
            val result = TaxiStub().postTaxiBookingInformation(true, bookingInfo)
            toast =
                    if (result == null) Toast.makeText(this, "エラー", Toast.LENGTH_SHORT)
                    else Toast.makeText(this, result, Toast.LENGTH_SHORT)
            toast!!.show()
             */
        }
}