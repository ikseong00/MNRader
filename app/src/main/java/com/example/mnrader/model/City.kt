package com.example.mnrader.model

enum class City(val code: Int, val displayName: String) {
    SEOUL(1, "서울특별시"),
    BUSAN(2, "부산광역시"),
    DAEGU(3, "대구광역시"),
    INCHEON(4, "인천광역시"),
    GWANGJU(5, "광주광역시"),
    DAEJEON(6, "대전광역시"),
    ULSAN(7, "울산광역시"),
    SEJONG(8, "세종특별자치시"),
    GYEONGGI(9, "경기도"),
    GANGWON(10, "강원특별자치도"),
    CHUNGBUK(11, "충청북도"),
    CHUNGNAM(12, "충청남도"),
    JEONBUK(13, "전라북도"),
    JEONNAM(14, "전라남도"),
    GYEONGBUK(15, "경상북도"),
    GYEONGNAM(16, "경상남도"),
    JEJU(17, "제주특별자치도");

    companion object {
        fun fromCode(code: Int): City? = City.entries.find { it.code == code }
    }
}