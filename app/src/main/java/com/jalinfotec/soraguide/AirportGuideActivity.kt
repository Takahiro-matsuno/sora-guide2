package com.jalinfotec.soraguide

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jalinfotec.kankoannai.R
import kotlinx.android.synthetic.main.activity_airport_guide.*
import org.jetbrains.anko.startActivity


class   AirportGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_guide)


        //表示画像ようの変数
        val images = listOf(R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon)

        //店舗名
        val names = Array(10,{i -> "お店$i"})

        //店舗説明
        val descriptions  = Array(10,{i -> "お店($i)の説明文です。このお店は、、、"})

        //店舗情報のDataクラス　Beanのようなもの？
        val flowers = List(names.size) { i -> FlowerData(names[i], descriptions[i], images[i])}


//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
//        listView.adapter = adapter

        val adapter = FlowerListAdapter(this, flowers)
        listView.adapter = adapter




        var list = findViewById<ListView>(R.id.listView)

            list.setOnItemClickListener{parent, view, position, id ->

                startActivity<AirportGuideDetailActivity>()

//                val name = view.findViewById<TextView>(R.id.nameTextView).text
//
//            AlertDialog.Builder(this)
//                .setTitle("$name")
//                .setMessage("($name)が選択されました。詳細へ遷移します。")
//                .setPositiveButton("OK"){dialog,which ->}.show()
        }












    }
}

//店舗情報のDataクラス
data class FlowerData(val name: String, val desc: String, val imageId: Int)

//画面に表示されるデータを保持しておくハコ
data class ViewHolder(val nameTextView: TextView, val descTextView: TextView, val flowerImgView: ImageView)

//独自アダプターの作成
//class ShopListAdapter(context: Context, shops: List<ShopData>) : ArrayAdapter<ShopData>(context, 0, shops){
//
//    //レイアウトファイルを作成するためのもの
//    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//    //レイアウトにデータを設定する
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//
//        //変数宣言
//        var view = convertView
//        var holder: ViewHolder
//
//        if(view == null){
//            view= layoutInflater.inflate(R.layout.list_airport_guide, parent, false)
//            holder = ViewHolder(
//                view?.titleTextView!!,
//                view.contentTextView,
//                view.shopImageView
//            )
//            view.tag = holder
//        }else{
//            holder = view.tag as ViewHolder
//        }
//
//        val flower = getItem(position) as ShopData
//        holder.titleTextView.text = flower.name
//        holder.contentTextView.text = flower.desc
//        holder.shopImageView.setImageBitmap(BitmapFactory.decodeResource(context.resources, flower.imageId))
//
//        return view
//
//    }


class FlowerListAdapter(context: Context, flowers: List<FlowerData>) : ArrayAdapter<FlowerData>(context, 0, flowers) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_airport_guide, parent, false)
            holder = ViewHolder(
                view?.findViewById(R.id.nameTextView)!!,
                view.findViewById(R.id.descTextView),
                view.findViewById(R.id.flowerImgView)
            )
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val flower = getItem(position) as FlowerData
        holder.nameTextView.text = flower.name
        holder.descTextView.text = flower.desc
        holder.flowerImgView.setImageBitmap(BitmapFactory.decodeResource(context.resources, flower.imageId))

        return view
    }
}
