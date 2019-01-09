package com.jalinfotec.kankoannai

import java.sql.Time
import java.util.*

class TaxiBookingInformation(
    val bookingId: String?,             // 予約番号
    val bookingStatus: Int,             // ステータス
    val rideOnDate: Date,               // 乗車日付
    //val rideOnTime: Time,               // 乗車時間
    val adultPassengerNumber: Int,      // 人数（大人）
    val childPassengerNumber: Int,      // 人数（子供）
    val taxiNumber: Int,                // 配車台数
    val companyId: String,              // 会社ID
    val destination: String?,           // 目的地
    val name: String,                   // 氏名
    val phonetic: String,               // フリガナ
    val userPhoneNumber: String,        // 電話番号
    val mailAddress: String,            // メールアドレス
    val userComment: String?,           // 利用者コメント
    val carNumber: String?,             // 車両番号
    val companyPhoneNumber: String?,    // 連絡先
    val taxiNotice: String?             // ご連絡事項
)