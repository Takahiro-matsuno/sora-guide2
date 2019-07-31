package com.jalinfotec.kankoannai.wikitude

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jalinfotec.kankoannai.Constants
import com.jalinfotec.kankoannai.R
import com.jalinfotec.kankoannai.wikitude.geo.GeoActivity
import com.jalinfotec.kankoannai.wikitude.image.SampleCamActivity
import kotlinx.android.synthetic.main.activity_wikitude.*
import org.jetbrains.anko.startActivity

class WikitudeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wikitude)

        image.setOnClickListener {
            startActivity<SampleCamActivity>()
        }
        geo_image.setOnClickListener {
            startActivity<GeoActivity>(Pair(GeoActivity.geoKey, "graphic"))
        }
    }

    override fun onResume() {
        super.onResume()
        val permList = ArrayList<String>()
        for (p in Constants.permList) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                permList.add(p)
            }
        }
        // 権限がない場合は許可を求めるダイアログを表示する
        if (permList.any()) {
            val permissions = permList.toArray(arrayOfNulls<String>(permList.size))
            requestPermissions(permissions, 0)
        }
    }
}
