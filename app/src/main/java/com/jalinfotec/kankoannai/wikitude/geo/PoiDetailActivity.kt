package com.jalinfotec.kankoannai.wikitude.geo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jalinfotec.kankoannai.R
import kotlinx.android.synthetic.main.activity_poi_detail.*

class PoiDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_KEY_POI_ID = "POI_ID"
        const val EXTRAS_KEY_POI_TITILE = "POI_TITLE"
        const val EXTRAS_KEY_POI_DESCR = "POI_DESCR"
    }

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var descr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_detail)

        if (savedInstanceState != null) {
            id = savedInstanceState.getString(EXTRAS_KEY_POI_ID) ?: ""
            title = savedInstanceState.getString(EXTRAS_KEY_POI_TITILE) ?: ""
            descr = savedInstanceState.getString(EXTRAS_KEY_POI_DESCR) ?: ""

        } else {
            id = intent.getStringExtra(EXTRAS_KEY_POI_ID)
            title = intent.getStringExtra(EXTRAS_KEY_POI_TITILE)
            descr = intent.getStringExtra(EXTRAS_KEY_POI_DESCR)
        }
        geo_id.text = id
        geo_title.text = title
        geo_descr.text = descr
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(EXTRAS_KEY_POI_ID, id)
        outState?.putString(EXTRAS_KEY_POI_TITILE, title)
        outState?.putString(EXTRAS_KEY_POI_DESCR, descr)
    }
}
