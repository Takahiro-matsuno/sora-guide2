package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {
    //プルダウンの選択肢
    private val spinnerItems = arrayOf(
        "高知県",
        "徳島県",
        "香川県",
        "愛媛県")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val spinner = findViewById<Spinner>(R.id.spinner)

        // ArrayAdapter
        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            spinnerItems)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // spinner に adapter をセット
        // Kotlin Android Extensions
        spinner.adapter = adapter

        // リスナーを登録
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                // Kotlin Android Extensions
                //textView.text = item
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }
        //ボタン押下された時の処理
        val chooseButton = findViewById<Button>(R.id.chooseButtun)
        chooseButton.setOnClickListener {
            Log.d("TEST","選択したもので表示するボタンが押下されたよ！")
        }
    }
}
