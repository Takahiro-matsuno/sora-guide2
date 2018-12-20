package com.jalinfotec.kankoannai

import android.util.Log
import com.google.gson.Gson
import java.lang.Exception

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
    fun postTaxiBookingInformation(success: Boolean, infoStr: String): String? {
        return if (success) {
            try {
                Gson().fromJson(infoStr, TaxiBookingInformation::class.java)
                "予約を受け付けました"
            } catch (e: Exception) {
                Log.d(logTag, e.message)
                null
            }
        } else "予約に失敗しました"

    }
    fun getTaxiBookingInfo(success: Boolean): String? {

        return if (success) {

            "[" +
                "{" +
                    "\"bookingId\":\"00001\"," +
                    "\"bookingStatus\":\"予約中\"," +
                    "\"rideOnData\":\"2010-12-01\"," +
                    "\"rideOnTime\":\"12:00:00\"," +
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
                    "\"bookingStatus\":\"編集中\"," +
                    "\"rideOnData\":\"2010-12-01\"," +
                    "\"rideOnTime\":\"12:00:00\"," +
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
                    "\"bookingStatus\":\"キャンセル済み\"," +
                    "\"rideOnData\":\"2010-12-01\"," +
                    "\"rideOnTime\":\"12:00:00\"," +
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
                    "\"companyPhoneNumber\":\"ほげほげほげほげ\"," +
                "}," +
            "]"
        } else null
    }
}
