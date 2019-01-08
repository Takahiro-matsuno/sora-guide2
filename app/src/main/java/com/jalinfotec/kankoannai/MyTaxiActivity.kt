package com.jalinfotec.kankoannai

import android.content.Context
import android.content.Intent
import org.jetbrains.anko.startActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jalinfotec.kankoannai.R.id.*
import kotlinx.android.synthetic.main.activity_my_taxi.*
import kotlinx.android.synthetic.main.activity_my_taxi_detail.*
import org.jetbrains.anko.find
import java.text.SimpleDateFormat

class MyTaxiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi)
        // アニメーションの無効化
        overridePendingTransition(0, 0)

        // 指定したタブを選択状態にする
        tabLayout.getTabAt(1)!!.select()

        //予約リストの読み込み
        var bookingInfo: String? = TaxiStub().getTaxiBookingInfo(true)
        var bookingList: List<TaxiBookingInformation>

        //予約リストをTaxiBookingInformationに型変換
        bookingList =
                try {
                    val type = object : TypeToken<List<TaxiBookingInformation>>() {}.type
                    var gSon = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
                    //gSon =  GsonBuilder().setDateFormat("HH:mm:ss")
                    //val gSon =  GsonBuilder().setDateFormat("yyyy-MM-dd","HH:mm:ss").create()
                    gSon.fromJson<List<TaxiBookingInformation>>(bookingInfo, type)
                } catch (e: Exception) {
                    Log.i("予約情報取得エラー", "${e.message}")
                    emptyList()
                }

        //取得した予約情報を画面表示用に変換するアダプタの生成
        val adapter = MyTaxiListAdapter(this, bookingList)
        //画面表示
        val listView: ListView = findViewById(R.id.myTaxiBookingList)
        listView.adapter = adapter

        //リストのタップイベント
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val bookingId = bookingList[position].bookingId
            //Toast.makeText(this,"$bookingId",Toast.LENGTH_SHORT)
            var intent = Intent(this, MyTaxiDetailActivity::class.java)
            intent.putExtra("ID",bookingId)
            startActivity(intent)
            this.finish()
        }


        // タブのタップイベント
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 選択中のタブがユーザーによって再度選択されたときに呼び出される
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            // タブが選択された状態を終了したときに呼び出される
            override fun onTabSelected(p0: TabLayout.Tab?) {}

            // タブが選択状態になると呼び出される
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                        startActivity<TaxiReservationActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                    1 -> {
                        // TODO 確認画面表示中に予約確認をタップした場合、画面をクリアするか確認する
                        startActivity<MyTaxiActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                }
                finish()
            }
        })

    }
}

class MyTaxiListAdapter(context: Context, val myTaxiList: List<TaxiBookingInformation>) :
    ArrayAdapter<TaxiBookingInformation>(context, 0, myTaxiList) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: layoutInflater.inflate(R.layout.my_taxi_list_item, parent, false)
        val df = SimpleDateFormat("yyyy/MM/dd")
        val tf = SimpleDateFormat("hh:mm")
        view.find<TextView>(bookingIdView).text = myTaxiList[position].bookingId
        view.find<TextView>(rideOnDateView).text = df.format(myTaxiList[position].rideOnDate)
        view.find<TextView>(rideOnTimeView).text = tf.format(myTaxiList[position].rideOnDate)
        view.find<TextView>(bookingStatusView).text = Integer.toString(myTaxiList[position].bookingStatus)

        return view
    }
}
/*
override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    var view = convertView
    var holder: ViewHolder

    if (view == null) {
        view = layoutInflater.inflate(R.layout.my_taxi_list_item, parent, false)
        view.find<TextView>(bookingIdView).text = ""
        holder = ViewHolder(
            view.find(bookingIdView),
            view.find(rideOnDateView),
            view.find(rideOnTimeView),
            view.find(bookingIdView),
        )
        view.tag = holder
    } else {
        holder = view.tag as ViewHolder
    }
    val bookingData = getItem(position) as TaxiBookingInformation
    val df = SimpleDateFormat("yyyy/MM/dd")
    val tf = SimpleDateFormat("hh:mm")
    holder.bookingId.text = bookingData.bookingId
    holder.rideOnDate.text = df.format(bookingData.rideOnDate)
    holder.rideOnTime.text = tf.format(bookingData.rideOnDate)
    holder.bookingStatus.text = Integer.toString(bookingData.bookingStatus)

    return view!!
}
}

data class ViewHolder(
val bookingId: TextView,
val rideOnDate: TextView,
val rideOnTime: TextView,
val bookingStatus: TextView
)*/