package com.jalinfotec.kankoannai

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.MediaController
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_my_taxi.*
import org.jetbrains.anko.startActivity
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
                    var gSon = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                    //gSon =  GsonBuilder().setDateFormat("HH:mm:ss")
                    //val gSon =  GsonBuilder().setDateFormat("yyyy-MM-dd","HH:mm:ss").create()
                    gSon.fromJson<List<TaxiBookingInformation>>(bookingInfo, type)
                } catch (e: Exception) {
                    Log.i("えらあああああ", "${e.message}")
                    emptyList()
                }
        //取得した予約情報を画面表示用に変換するアダプタの生成
        val adapter = MyTaxiListAdapter(applicationContext, bookingList)
        //画面表示
        val listView: ListView = findViewById(R.id.myTaxiBookingList)
        listView.adapter = adapter

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

class MyTaxiListAdapter(context: Context, MyTaxiList: List<TaxiBookingInformation>) :
    ArrayAdapter<TaxiBookingInformation>(context, 0, MyTaxiList) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        var holder: ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.my_taxi_list_item, parent, false)
            /*holder = ViewHolder(
                view?.bookingId!!,
            )*/
            //view.tag = holder
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

        return view
    }
}

data class ViewHolder(
    val bookingId: TextView,
    val rideOnDate: TextView,
    val rideOnTime: TextView,
    val bookingStatus: TextView
)