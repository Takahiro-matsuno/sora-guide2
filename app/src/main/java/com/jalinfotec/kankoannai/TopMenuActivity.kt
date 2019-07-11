package com.jalinfotec.kankoannai

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import com.jalinfotec.kankoannai.wikitude.WikitudeMainActivity
import org.jetbrains.anko.startActivity

class TopMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_menu)

        //空港案内画面へ遷移
        val toAirportButton = findViewById<Button>(R.id.airport)
        toAirportButton.setOnClickListener{

            startActivity<AirportGuideActivity>()
        }

        //フライト情報画面へ遷移
        val toFlightInfoButton = findViewById<Button>(R.id.flightinfo)
        toFlightInfoButton.setOnClickListener{

            startActivity<AirportGuideActivity>()
        }

        //タクシー予約画面へ遷移
        val toTaxyButton = findViewById<Button>(R.id.taxi)
        toTaxyButton.setOnClickListener{

            startActivity<AirportGuideActivity>()
        }

        //観光案内画面へ遷移
        val toSightseeingButton = findViewById<Button>(R.id.sightseeing)
        toSightseeingButton.setOnClickListener{
            startActivity<KankoMainActivity1>()
        }

        // wikitude
        findViewById<Button>(R.id.wikitude).setOnClickListener {
            startActivity<WikitudeMainActivity>()
        }

        val affiliateButton = findViewById<Button>(R.id.affiliate)
        affiliateButton.setOnClickListener {


            AlertDialog.Builder(this)
                .setTitle("広告")
                .setMessage("ここは広告エリアです。タップすると広告ページに遷移します。")
                .setPositiveButton("OK"){dialog,which ->}.show()
        }



    }
}
