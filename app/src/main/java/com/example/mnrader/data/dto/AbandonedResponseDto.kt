package com.example.mnrader.data.dto


import com.google.gson.annotations.SerializedName

data class AbandonedResponseDto(
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("body")
        val body: Body,
        @SerializedName("header")
        val header: Header
    ) {
        data class Body(
            @SerializedName("items")
            val items: Items,
            @SerializedName("numOfRows")
            val numOfRows: Int,
            @SerializedName("pageNo")
            val pageNo: Int,
            @SerializedName("totalCount")
            val totalCount: Int
        ) {
            data class Items(
                @SerializedName("item")
                val item: List<Item>
            ) {
                data class Item(
                    @SerializedName("age")
                    val age: String,
                    @SerializedName("careAddr")
                    val careAddr: String,
                    @SerializedName("careNm")
                    val careNm: String,
                    @SerializedName("careOwnerNm")
                    val careOwnerNm: String,
                    @SerializedName("careRegNo")
                    val careRegNo: String,
                    @SerializedName("careTel")
                    val careTel: String,
                    @SerializedName("colorCd")
                    val colorCd: String,
                    @SerializedName("desertionNo")
                    val desertionNo: String,
                    @SerializedName("etcBigo")
                    val etcBigo: String?,
                    @SerializedName("happenDt")
                    val happenDt: String,
                    @SerializedName("happenPlace")
                    val happenPlace: String,
                    @SerializedName("kindCd")
                    val kindCd: String,
                    @SerializedName("kindFullNm")
                    val kindFullNm: String,
                    @SerializedName("kindNm")
                    val kindNm: String,
                    @SerializedName("neuterYn")
                    val neuterYn: String,
                    @SerializedName("noticeEdt")
                    val noticeEdt: String,
                    @SerializedName("noticeNo")
                    val noticeNo: String,
                    @SerializedName("noticeSdt")
                    val noticeSdt: String,
                    @SerializedName("orgNm")
                    val orgNm: String,
                    @SerializedName("popfile1")
                    val popfile1: String?,
                    @SerializedName("popfile2")
                    val popfile2: String?,
                    @SerializedName("processState")
                    val processState: String,
                    @SerializedName("rfidCd")
                    val rfidCd: String?,
                    @SerializedName("sexCd")
                    val sexCd: String,
                    @SerializedName("sfeHealth")
                    val sfeHealth: String?,
                    @SerializedName("sfeSoci")
                    val sfeSoci: String?,
                    @SerializedName("specialMark")
                    val specialMark: String,
                    @SerializedName("upKindCd")
                    val upKindCd: String,
                    @SerializedName("upKindNm")
                    val upKindNm: String,
                    @SerializedName("updTm")
                    val updTm: String,
                    @SerializedName("weight")
                    val weight: String
                )
            }
        }

        data class Header(
            @SerializedName("reqNo")
            val reqNo: Int,
            @SerializedName("resultCode")
            val resultCode: String,
            @SerializedName("resultMsg")
            val resultMsg: String
        )
    }
}