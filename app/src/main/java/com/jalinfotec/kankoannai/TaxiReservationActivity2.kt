package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import kotlinx.android.synthetic.main.activity_taxi_reservation2.*
import kotlinx.android.synthetic.main.app_bar_taxi.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class TaxiReservationActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var webView: WebView
    private lateinit var cookieManager: CookieManager

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation2)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // テスト
        val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/"
        // 登録画面
        //val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/app/registration"
        // localhostで実行しているWebアプリケーション(テスト用)
        //val url = "http://10.0.2.2:8080"

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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * ヘッダ右のボタン押下時の処理
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    /**
     * ヘッダ右のボタン内メニュー押下時の処理
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * ドロワーメニュー内の項目選択時の処理
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_top -> {
                finish()
            }
            R.id.nav_airport -> {
                startActivity<AirportGuideActivity>()
                finish()
            }
            R.id.nav_flight -> {
                startActivity<AirportGuideActivity>()
                finish()
            }
            R.id.nav_tourism -> {
                startActivity<KankoMainActivity1>()
                finish()
            }
            R.id.nav_taxi -> {
                val url = "https://taxiapptest.azurewebsites.net/taxiReservation-0.1.2/"
                webView.loadUrl(url)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
