package com.example.mnrader.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LostPortalResponseDto(
    @SerialName("response")
    val response: Response
) {
    @Serializable
    data class Response(
        @SerialName("body")
        val body: Body,
        @SerialName("header")
        val header: Header
    ) {
        @Serializable
        data class Body(
            @SerialName("items")
            val items: Items,
            @SerialName("numOfRows")
            val numOfRows: Int,
            @SerialName("pageNo")
            val pageNo: Int,
            @SerialName("totalCount")
            val totalCount: Int
        ) {
            @Serializable
            data class Items(
                @SerialName("item")
                val item: List<Item>
            ) {
                @Serializable
                data class Item(
                    @SerialName("age")
                    val age: String,
                    @SerialName("callName")
                    val callName: String,
                    @SerialName("callTel")
                    val callTel: String,
                    @SerialName("colorCd")
                    val colorCd: String,
                    @SerialName("happenAddr")
                    val happenAddr: String,
                    @SerialName("happenAddrDtl")
                    val happenAddrDtl: String?,
                    @SerialName("happenDt")
                    val happenDt: String,
                    @SerialName("happenPlace")
                    val happenPlace: String,
                    @SerialName("kindCd")
                    val kindCd: String,
                    @SerialName("orgNm")
                    val orgNm: String,
                    @SerialName("popfile")
                    val popfile: String,
                    @SerialName("rfidCd")
                    val rfidCd: String?,
                    @SerialName("sexCd")
                    val sexCd: String,
                    @SerialName("specialMark")
                    val specialMark: String
                )
            }
        }

        @Serializable
        data class Header(
            @SerialName("reqNo")
            val reqNo: Int,
            @SerialName("resultCode")
            val resultCode: String,
            @SerialName("resultMsg")
            val resultMsg: String
        )
    }
}