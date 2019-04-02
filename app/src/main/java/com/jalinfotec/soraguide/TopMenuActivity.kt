package com.jalinfotec.soraguide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import com.jalinfotec.soraguide.R
import org.jetbrains.anko.startActivity

class TopMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_menu)

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


        //要修正：アフィリエイトリンクエリアの動作
        val affiliateButton = findViewById<Button>(R.id.affiliate)
        affiliateButton.setOnClickListener {


            AlertDialog.Builder(this)
                .setTitle("広告")
                .setMessage("ここは広告エリアです。タップすると広告ページに遷移します。")
                .setPositiveButton("OK"){dialog,which ->}.show()
        }



    }
}
