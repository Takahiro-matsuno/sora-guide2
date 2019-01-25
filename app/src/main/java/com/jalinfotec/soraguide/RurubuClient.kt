package com.jalinfotec.kankoannai

import android.content.ClipData
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

//このインターフェースの役割⇒アクセス先URL指定・どのクラスに格納するかを指定する箇所
//これがRetrofitのクライアントクラスであり、APIのリクエスト先のエンドポイント

interface RurubuAPI {
    @GET("/appif/sight")
    fun items(@Query("appid")appid:String,
              @Query("keywords") keywords: String,
              @Query("kwtype") kwtype: Int,
              @Query("responsetype") respnsetype: String):Call<List<RurubuItems>>

    companion object {
        fun create():RurubuAPI{
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://www.j-jti.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RurubuAPI::class.java)
        }
    }
}

    //getの中にはbaseアドレス以降～?までを記載
//    @GET("/appif/sight")
    //ここ何書けばよいの？
    //Queryの中にURLの?以降を記載する
    //Callに記載するのは自分で定義するModelクラス
    //Modelクラスとは受け取りするデータのクラスのこと
//    fun items(
//        @Query("appid")appid:String,
//        @Query("keywords") keywords: String,
//        @Query("kwtype") kwtype: Int,
//        @Query("responsetype") respnsetype: String
//    ): Call<RurubuItems>

//APIクライアントのインスタンスは実行するMainActivityに書く。



