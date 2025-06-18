package com.example.mnrader.model

enum class City(val code: Int, val displayName: String, val orgCd: String) {
    SEOUL(1, "서울특별시", "6110000"),
    BUSAN(2, "부산광역시", "6260000"),
    DAEGU(3, "대구광역시", "6270000"),
    INCHEON(4, "인천광역시", "6280000"),
    GWANGJU(5, "광주광역시", "6290000"),
    SEJONG(6, "세종특별자치시", "5690000"),
    DAEJEON(7, "대전광역시", "6300000"),
    ULSAN(8, "울산광역시", "6310000"),
    GYEONGGI(9, "경기도", "6410000"),
    GANGWON(10, "강원특별자치도", "6530000"),
    CHUNGBUK(11, "충청북도", "6430000"),
    CHUNGNAM(12, "충청남도", "6440000"),
    JEONBUK(13, "전라북도", "6540000"), // JSON에 따라 전라북도 → 전북특별자치도
    JEONNAM(14, "전라남도", "6460000"),
    GYEONGBUK(15, "경상북도", "6470000"),
    GYEONGNAM(16, "경상남도", "6480000"),
    JEJU(17, "제주특별자치도", "6500000");

    override fun toString(): String = displayName

    companion object {
        fun fromOrgCd(orgCd: String): City? = City.entries.find { it.orgCd == orgCd }
        fun fromDisplayName(name: String): City? = City.entries.find { it.displayName == name }
    }
}
