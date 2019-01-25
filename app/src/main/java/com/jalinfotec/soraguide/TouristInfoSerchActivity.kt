package com.jalinfotec.kankoannai

import android.app.LauncherActivity
import android.app.usage.UsageEvents
import android.arch.lifecycle.Lifecycle
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.util.EventLog
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.jalinfotec.kankoannai.R.id.text
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class KankoMainActivity1 : AppCompatActivity(),AnkoLogger {
    //プルダウンの選択肢
    private val spinnerItems = arrayOf(
        "高知県",
        "徳島県",
        "香川県",
        "愛媛県"
    )

    //quita アプリ実装のサイトを参考にした処理 ここから
    lateinit private var mAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.rurubu_list_item)

        mAdapter = ListAdapter(this, R.layout.rurubu_list_item)

        val listView = findViewById<ListView>(R.id.list_view) as ListView
        listView.adapter = mAdapter

        val editText = findViewById<EditText>(R.id.edit_text) as EditText
        editText.setOnKeyListener(OnkeyListener())
    }

    //ListAdapterは行を表示する処理
    //↓で出てくるImageView,TextViewはrurubu_list_itemに記載した項目と連動
    private inner class ListAdapter(context: Context, resource: Int) : ArrayAdapter<RurubuItems>(context, resource) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                //再利用可能なviewがないときは作成
                convertView = layoutInflater.inflate(R.layout.rurubu_list_item, null)
            }

            val imageView = convertView?.findViewById<ImageView>(R.id.image_view) as ImageView
            val itemTitleView = convertView.findViewById<TextView>(R.id.item_title) as TextView
            val addressView = convertView.findViewById<TextView>(R.id.traffic1) as TextView

            imageView.setImageBitmap(null)//再利用された時の残っている画像の削除

            // 表示する行番号のデータを取り出す
            val result = getItem(position)

            //定義したGSONクラスモデルの項目を右辺に記載。ここでは一覧に表示したいtitleとtraffic1を記載
            //Picasso.with(context).load(result.user?.profile_image_url).into(imageView)
            itemTitleView.text = result.SightList.Title
            addressView.text = result.SightList.Traffic1

            return convertView
        }
    }

    //検索処理部分
    //enterキーがあがったら検索処理実行、実行結果をAdapterに詰める処理
    private inner class OnkeyListener : View.OnKeyListener {
        override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
            //keyEventではなく、KeyEventなので注意！間違うと定義が違うのでunresolveになる
            if (keyEvent?.action != KeyEvent.ACTION_UP || keyCode != KeyEvent.KEYCODE_ENTER ) {
                return false
            }

            val editText = view as EditText
            //キーボード閉じる
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)

            var text = editText.text.toString()
            try {
                text = URLEncoder.encode(text, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                Log.e("", e.toString(), e)
                return true
            }

            if (!TextUtils.isEmpty(text)) {
                val request = RurubuAPI.create().items("jtzY6LZYK8226ibN", text, 2, "json")
                Log.d("", request.request().url().toString())
                val items = object : retrofit2.Callback<List<RurubuItems>>{
                    override fun onResponse(call: Call<List<RurubuItems>>?, response: Response<List<RurubuItems>>?) {
                        mAdapter.clear()
                        response?.body()?.forEach { mAdapter.add(it) }
                    }

                     override fun onFailure(call: Call<List<RurubuItems>>?,t:Throwable?) {
                    }
                }
                request.enqueue(items)
            }
            return true
        }
    }

}





    //ここまで


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val editText = findViewById<EditText>()
//        //val spinner = findViewById<Spinner>(R.id.spinner)
//
//        // ArrayAdapter
//        val adapter = ArrayAdapter(
//            applicationContext,
//            android.R.layout.simple_spinner_item,
//            spinnerItems)
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        // Kotlin Android Extensions
//        spinner.adapter = adapter
//
////        fun toKanko2(view: View){
////            //渡す引数
////            val Region = "adapter"
////            startActivity<KankoMainActivity2>("REIGION_KEY" to Region)
////        }
//        //ボタン押下された時の処理
//        val chooseButton = findViewById<Button>(R.id.chooseButtun) as Button
//        chooseButton.setOnClickListener {
//            //ボタンが叩かれた時の処理
//            val Region = "adapter"
//            //startActivity<KankoMainActivity2>("REIGION_KEY" to Region)
//            Log.d("TEST","選択したもので表示するボタンが押下されたよ！")
//
//
//            //ここから追加
//            val editText = view as EditText
//            //キーボード閉じる
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(editText.windowToken,0)
//
//            var text = editText.text.toString()
//
//            if(!TextUtils.isEmpty(text)){
//                var request = RurubuAPI.create().items(text,"","","")
//                Log.d("",request.request().url().toString())
//                val item = object :Callback<List<ClipData.Item>>{
//                    fun onResponse(call: Call<List<ClipData.Item>>?,response: Response<List<ClipData.Item>>?){
//                        mAdapter.clear()
//                        response?.body()?.forEach { mAdapter.add(it) }
//                    }
//                    fun onFailure(call: Call<List<ClipData.Item>>?,t:Throwable?){
//                    }
//                }
//                request.enqueue(item)
//            }
//            return@setOnClickListener
//        }
//    }








