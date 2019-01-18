package com.jalinfotec.kankoannai
//classは分けなくてもよい？

data class RurubuItems(
    val PageNo: Int,
    val SightList: Sight,
    val TotalPages: Int,
    val TotalResults: Int
)
