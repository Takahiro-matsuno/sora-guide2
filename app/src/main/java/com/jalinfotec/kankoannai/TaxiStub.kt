package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.jalinfotec.kankoannai.R.string.bookingId
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaxiStub {

    private val logTag = this::class.java.name

    fun getTaxiCompanyInformation(success: Boolean): String? {
        return if (success) {
            "[" +
                    "{" +
                    "\"companyId\" : \"hogehoge\"," +
                    "\"companyName\": \"ほげタクシー\"," +
                    "\"companyPhoneNumber\": 1111," +
                    "\"companyAddress\": \"aaa\"" +
                    "}," +
                    "{" +
                    "\"companyId\" : \"piyopiyo\"," +
                    "\"companyName\": \"p＼ぴよタクシー\"," +
                    "\"companyPhoneNumber\": 2222," +
                    "\"companyAddress\": \"bbb\"" +
                    "}," +
                    "{" +
                    "\"companyId\" : \"hugahuga\"," +
                    "\"companyName\": \"ふがタクシー\"," +
                    "\"companyPhoneNumber\": 3333," +
                    "\"companyAddress\": \"ccc\"" +
                    "}" +
                    "]"
        } else null
    }

    fun postTaxiBookingInformation(success: Boolean, bookingInfo: TaxiBookingInformation): String? {
        return if (success) {
            try {
                val str = Gson().toJson(bookingInfo, TaxiBookingInformation::class.java)
                Log.d(logTag, "json:$str")
                "予約を受け付けました"
            } catch (e: Exception) {
                Log.d(logTag, e.message)
                null
            }
        } else null
    }

    fun getTaxiBookingInfo(success: Boolean): String? {

        return if (success) {

            "[" +
                    "{" +
                    "\"bookingId\":\"00001\"," +
                    "\"bookingStatus\":\"1\"," +
                    "\"rideOnDate\":\"2010-12-01T00:00:00.000Z\"," +
                    //"\"rideOnTime\":\"12:00:00\"," +
                    "\"adultPassengerNumber\":2," +
                    "\"childPassengerNumber\":3," +
                    "\"taxiNumber\":2," +
                    "\"companyId\":\"1110\"," +
                    "\"destination\":\"お台場\"," +
                    "\"name\":\"ほげほげ\"," +
                    "\"phonetic\":\"ほげほげ\"," +
                    "\"userPhoneNumber\":\"09065733926\"," +
                    "\"mailAddress\":\"ほげほげ\"," +
                    "\"userComment\":\"ほげほげほげ\"," +
                    "\"carNumber\":\"ほげほげほげ\"," +
                    "\"companyPhoneNumber\":\"ほげほげほげほげ\"," +
                    "\"taxiNotice\":\"ほげー\"" +
                    "}," +
                    "{" +
                    "\"bookingId\":\"00002\"," +
                    "\"bookingStatus\":\"2\"," +
                    "\"rideOnDate\":\"2010-12-01T00:00:00.000Z\"," +
                    //"\"rideOnTime\":\"12:00:00\"," +
                    "\"adultPassengerNumber\":2," +
                    "\"childPassengerNumber\":3," +
                    "\"taxiNumber\":2," +
                    "\"companyId\":\"1110\"," +
                    "\"destination\":\"お台場\"," +
                    "\"name\":\"ぴよぴよ\"," +
                    "\"phonetic\":\"ほげほげ\"," +
                    "\"userPhoneNumber\":\"09065733926\"," +
                    "\"mailAddress\":\"ほげほげ\"," +
                    "\"userComment\":\"ほげほげほげ\"," +
                    "\"carNumber\":\"ほげほげほげ\"," +
                    "\"companyPhoneNumber\":\"ほげほげほげほげ\"," +
                    "\"taxiNotice\":\"ほげー\"" +
                    "}," +
                    "{" +
                    "\"bookingId\":\"00003\"," +
                    "\"bookingStatus\":\"3\"," +
                    "\"rideOnDate\":\"2010-12-01T00:00:00.000Z\"," +
                    //"\"rideOnTime\":\"12:00:00\"," +
                    "\"adultPassengerNumber\":2," +
                    "\"childPassengerNumber\":3," +
                    "\"taxiNumber\":2," +
                    "\"companyId\":\"1110\"," +
                    "\"destination\":\"お台場\"," +
                    "\"name\":\"ふがふが\"," +
                    "\"phonetic\":\"ほげほげ\"," +
                    "\"userPhoneNumber\":\"09065733926\"," +
                    "\"mailAddress\":\"ほげほげ\"," +
                    "\"userComment\":\"ほげほげほげ\"," +
                    "\"carNumber\":\"ほげほげほげ\"," +
                    "\"companyPhoneNumber\":\"ほげほげほげほげ\"" +
                    "}" +
                    "]"
        } else null
    }

    @SuppressLint("SimpleDateFormat")
    fun getTaxiBookingInfo(id: String): TaxiBookingInformation? {
        val bookingInfoList = ArrayList<TaxiBookingInformation>()

        for (count in 0..2) {
            val bookingInfo = TaxiBookingInformation(
                "0000" + (count+1).toString(),
                0,
                SimpleDateFormat("yyyy/MM/dd hh:mm").parse("2018/11/12 5:00"),
                count+1,
                1,
                1,
                "1000",
                "お台場",
                "なまえ",
                "ほげほげ",
                "ほげほげ",
                "めあど",
                "りようこめ",
                "しゃりょう番号",
                "れんらくさき",
                "ごあんないじこう"
            )
            bookingInfoList.add(bookingInfo)
        }

        for (count in 0..bookingInfoList.lastIndex) {
            if (id == bookingInfoList[count].bookingId) {
                return bookingInfoList[count]
            }
        }

        return null
    }
}
