package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.webkit.*
import org.jetbrains.anko.find


class TaxiReservationActivity : AppCompatActivity() {

    lateinit var webView: WebView
    private lateinit var cookieManager: CookieManager
    private lateinit var cookieSyncManager: CookieSyncManager

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation)

        // テスト
        //val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/"
        // 登録画面
        //val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/app/registration"
        // TODO その他適宜追加
        //localhostで実行しているWebアプリケーション
        val url = "http://10.0.2.2:8080"

        //Cookie有効化
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(applicationContext)
            CookieSyncManager.getInstance().startSync()
        }
        cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)


        //WebView設定
        webView = find(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        //UserAgent設定
        val userAgent = webView.settings.userAgentString
        webView.settings.userAgentString = userAgent + "sora-GuideApp"

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

    override fun onPause() {
        super.onPause()
        webView.pauseTimers()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush()
        } else {
            CookieSyncManager.getInstance().sync()
            CookieSyncManager.getInstance().stopSync()
        }
    }

    override fun onResume() {
        super.onResume()
        webView.resumeTimers()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush()
        } else {
            CookieSyncManager.getInstance().startSync()
            CookieSyncManager.getInstance().sync()
        }
    }

}
