package com.example.mnrader.model

enum class CatBreed(val id: Int, val breedName: String) {
    PERSIAN(101, "페르시안"),
    RUSSIAN_BLUE(102, "러시안 블루"),
    BRITISH_SHORTHAIR(103, "브리티시 쇼트헤어"),
    SIAMESE(104, "샴"),
    SCOTTISH_FOLD(105, "스코티시 폴드"),
    BENGAL(106, "벵갈"),
    MAINE_COON(107, "메인쿤"),
    TURKISH_ANGORA(108, "터키시 앙고라"),
    NORWEGIAN_FOREST_CAT(109, "노르웨이 숲 고양이"),
    AMERICAN_SHORTHAIR(110, "아메리칸 쇼트헤어"),
    RAGDOLL(111, "랙돌"),
    SIBERIAN(112, "시베리안"),
    DEVON_REX(113, "데본 렉스"),
    SPHYNX(114, "스핑크스"),
    BOMBAY(115, "봄베이"),
    KOREAN_SHORTHAIR(116, "코리안 쇼트헤어");

    companion object {
        fun fromId(id: Int): CatBreed? = CatBreed.entries.find { it.id == id }
    }
} 