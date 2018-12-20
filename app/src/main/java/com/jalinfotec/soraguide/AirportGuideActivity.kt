package com.jalinfotec.soraguide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_airport_guide.*
import android.widget.ListView
import java.io.StringBufferInputStream


class   AirportGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_guide)


        val items = Array(5,{i -> "お店$i"})
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter



    }
}
