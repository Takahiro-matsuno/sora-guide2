package com.jalinfotec.soraguide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_top_menu.*
import com.jalinfotec.soraguide.R
import org.jetbrains.anko.startActivity
import kotlin.concurrent.timer

class TopMenuActivity : AppCompatActivity() {

    //Admob用
    lateinit var mAdView : AdView

    //TOP画面表示画像のリスト作成→ここはDBから呼べるようにする？
    private val resource = listOf(
        R.drawable.topimage , R.drawable.floormap
    )

    //どの画像を表示しているかを保持する変数
    private var position = 0


    //画像スライドショー初期設定
    private var isSlideshow = true
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_menu)

        // Sample AdMob app ID: ca-app-pub-2003234893806822~5059310598
        MobileAds.initialize(this, "ca-app-pub-2003234893806822~5059310598")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        //ファクトリクラスのインスタンスをセットする。
        //makeViewメソッドをSAM変換により簡素化
        imageSwitcher.setFactory {
            //ImageViewのインスタンスを生成
            ImageView(this)
        }

        //初期画像の設定
        imageSwitcher.setImageResource(resource[0])


        timer(period = 5000){
            handler.post {
                if(isSlideshow){
                    movePosition(1)
                }
            }
        }

        //空港案内画面へ遷移
        val toAirportButton = findViewById<ImageButton>(R.id.airport)
        toAirportButton.setOnClickListener{

            //startActivity<AirportGuideActivity>()
        }

        //フライト情報画面へ遷移
        val toFlightInfoButton = findViewById<ImageButton>(R.id.flight)
        toFlightInfoButton.setOnClickListener{

           //startActivity<AirportGuideActivity>()
        }

        //タクシー予約画面へ遷移
        val toTaxyButton = findViewById<ImageButton>(R.id.taxi)
        toTaxyButton.setOnClickListener{

            //startActivity<AirportGuideActivity>()
        }

        //観光案内画面へ遷移
        val toSightseeingButton = findViewById<ImageButton>(R.id.sightseeing)
        toSightseeingButton.setOnClickListener{

           // startActivity<AirportGuideActivity>()
        }

//
//        //要修正：アフィリエイトリンクエリアの動作
//        val affiliateButton = findViewById<Button>(R.id.affiliate)
//        affiliateButton.setOnClickListener {
//
//
//            AlertDialog.Builder(this)
//                .setTitle("広告")
//                .setMessage("ここは広告エリアです。タップすると広告ページに遷移します。")
//                .setPositiveButton("OK"){dialog,which ->}.show()
//        }



    }

    private fun movePosition(move: Int){
        position += move
        //Positionが画像配列のサイズよりも大きい場合は0に戻す。
        if(position >= resource.size){
            position = 0
            //positionが0以下になった場合
        }else if(position < 0){
            position = resource.size - 1
        }
        imageSwitcher.setImageResource(resource[position])
    }
}
