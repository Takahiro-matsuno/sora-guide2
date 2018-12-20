package com.jalinfotec.kankoannai

class TaxiStub {
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
}
