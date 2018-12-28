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
}
