package com.example.mnrader.model

enum class CatBreed(val id: Int, val breedName: String, val kindCd: String?) {
    PERSIAN(101, "페르시안", "00214"),
    RUSSIAN_BLUE(102, "러시안 블루", "000172"),
    BRITISH_SHORTHAIR(103, "브리티시 쇼트헤어", "000181"),
    SIAMESE(104, "샴", "000184"),
    SCOTTISH_FOLD(105, "스코티시 폴드", "000188"),
    BENGAL(106, "벵갈", "000179"),
    MAINE_COON(107, "메인쿤", "000176"),
    TURKISH_ANGORA(108, "터키시 앙고라", "000195"),
    NORWEGIAN_FOREST_CAT(109, "노르웨이 숲 고양이", "000170"),
    AMERICAN_SHORTHAIR(110, "아메리칸 쇼트헤어", "000192"),
    RAGDOLL(111, "랙돌", "00213"),
    SIBERIAN(112, "시베리안", "000190"),
    DEVON_REX(113, "데본 렉스", "000171"),
    SPHYNX(114, "스핑크스", "000189"),
    BOMBAY(115, "봄베이", "000180"),
    KOREAN_SHORTHAIR(116, "코리안 쇼트헤어", "000200");

    companion object {
        fun fromId(id: Int): CatBreed? = entries.find { it.id == id }
        fun fromKindCd(kindCd: String): CatBreed? = entries.find { it.kindCd == kindCd }
        fun fromBreedName(name: String): CatBreed? = entries.find { it.breedName == name }
    }
} 