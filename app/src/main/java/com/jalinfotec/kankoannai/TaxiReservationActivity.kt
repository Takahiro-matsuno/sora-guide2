package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import org.jetbrains.anko.find

class TaxiReservationActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation)

        // テスト
        val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/"
        // 登録画面
        //val url = ""
        // TODO その他適宜追加
        //val url = ""

        webView = find(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
    // Android標準ボタンの制御
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // 戻るボタンを押された場合
        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // ブラウザバックができる場合はブラウザバックする
            webView.goBack()
            true
        } else super.onKeyDown(keyCode, event) // ブラウザバックできない場合はActivityしTopMenuへ戻る
    }
}
