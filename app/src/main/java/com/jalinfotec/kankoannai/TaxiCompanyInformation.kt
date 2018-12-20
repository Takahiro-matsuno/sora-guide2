package com.jalinfotec.kankoannai

data class TaxiCompanyInformation(
    val companyId: String,          // 会社ID
    val companyName: String,        // 会社名
    val companyPhoneNumber: Int,    // 連絡先
    val companyAddress: String      // 所在地
)