package com.example.mnrader.mapper

import com.example.mnrader.data.dto.AbandonedResponseDto
import com.example.mnrader.data.dto.animal.response.AnimalDetailResponseDto
import com.example.mnrader.data.dto.home.response.HomeAnimalDto
import com.example.mnrader.data.dto.home.response.HomeResponseDto
import com.example.mnrader.data.dto.user.response.AlarmAnimalDto
import com.example.mnrader.data.dto.user.response.MyAnimalDto
import com.example.mnrader.data.dto.user.response.MyPostAnimalDto
import com.example.mnrader.data.dto.user.response.UserAlarmResponseDto
import com.example.mnrader.data.dto.user.response.UserAnimalDetailResponseDto
import com.example.mnrader.data.dto.user.response.UserMyPageResponseDto
import com.example.mnrader.data.dto.user.response.UserMyPostResponseDto
import com.example.mnrader.data.dto.user.response.UserScrapAnimalDto
import com.example.mnrader.data.dto.user.response.UserScrapResponseDto
import com.example.mnrader.data.entity.LostAnimalEntity
import com.example.mnrader.model.City.Companion.fromCode
import com.example.mnrader.ui.animaldetail.viewmodel.MNAnimalDetail
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal
import com.example.mnrader.ui.mypost.viewmodel.MyPostModel
import com.example.mnrader.ui.notification.model.NotificationAnimalUiModel
import com.example.mnrader.ui.scrap.viewmodel.ScrapModel
import com.example.mnrader.ui.setting.model.UserAnimalDetailUiModel
import com.example.mnrader.ui.setting.screen.AnimalType
import com.naver.maps.geometry.LatLng

fun AbandonedResponseDto.toUiModel(): List<HomeAnimalData> =
    this.response.body.items.item.map {
        HomeAnimalData(
            id = it.desertionNo.toLong(),
            imageUrl = it.popfile1,
            name = it.kindFullNm,
            gender = when (it.sexCd) {
                "F" -> Gender.FEMALE
                else -> Gender.MALE
            },
            location = it.careAddr,
            date = it.updTm.split(" ").first(),
            type = AnimalDataType.PORTAL_PROTECT,
            latLng = LatLng(0.0, 0.0), // TODO: 위치 정보를 업데이트해야함.
            isBookmarked = false // TODO: 나중에 북마크 정보를 다시 할당해야함.
        )
    }

fun LostAnimalEntity.toUiModel(): HomeAnimalData =
    HomeAnimalData(
        id = this.id,
        imageUrl = this.popfile,
        name = this.kindCd,
        gender = this.sexCd,
        location = this.happenAddr,
        date = this.happenDate.split(" ").first(),
        type = AnimalDataType.PORTAL_LOST,
        latLng = LatLng(0.0, 0.0), // TODO: 위치 정보를 업데이트해야함.
        isBookmarked = false // TODO: 나중에 북마크 정보를 다시 할당해야함.
    )

fun MyAnimalDto.toUiModel(): MyAnimal =
    MyAnimal(
        imgUrl = this.img,
        name = this.name,
        id = this.animalUserId
    )

fun UserMyPageResponseDto.toMyAnimals(): List<MyAnimal> =
    this.myAnimal?.map { it.toUiModel() } ?: emptyList()

fun UserScrapAnimalDto.toUiModel(): ScrapModel =
    ScrapModel(
        id = this.animalId,
        name = this.name,
        address = fromCode(this.city).name,
        date = this.occurredAt,
        imageUrl = this.img,
        type = when (this.status) {
            "LOST" -> AnimalDataType.MN_LOST
            "PROTECTED" -> AnimalDataType.PORTAL_PROTECT
            "SIGHTING" -> AnimalDataType.MY_WITNESS
            else -> AnimalDataType.PORTAL_LOST
        }
    )

fun UserScrapResponseDto.toScrapModels(): List<ScrapModel> =
    this.scrapList?.map { it.toUiModel() } ?: emptyList()

fun HomeAnimalDto.toUiModel(): HomeAnimalData =
    HomeAnimalData(
        id = this.animalId,
        imageUrl = this.img,
        name = this.name,
        gender = when (this.gender) {
            "FEMALE" -> Gender.FEMALE
            else -> Gender.MALE
        },
        location = fromCode(this.city).name,
        date = this.occurredAt,
        type = when (this.status) {
            "LOST" -> AnimalDataType.MN_LOST
            "PROTECTED" -> AnimalDataType.PORTAL_PROTECT
            "SIGHTING" -> AnimalDataType.MY_WITNESS
            else -> AnimalDataType.PORTAL_LOST
        },
        latLng = LatLng(0.0, 0.0), // TODO: 위치 정보를 업데이트해야함.
        isBookmarked = false // TODO: 나중에 북마크 정보를 다시 할당해야함.
    )

fun HomeResponseDto.toHomeAnimalList(): List<HomeAnimalData> =
    this.animal?.map { it.toUiModel() } ?: emptyList()

fun MyPostAnimalDto.toUiModel(): MyPostModel =
    MyPostModel(
        id = this.animalId.toLong(),
        name = this.name,
        address = fromCode(this.city).name,
        date = this.occurredAt,
        imageUrl = this.img,
        gender = when (this.gender) {
            "FEMALE" -> Gender.FEMALE
            else -> Gender.MALE
        },
        type = when (this.status) {
            "LOST" -> AnimalDataType.PORTAL_LOST
            "PROTECTED" -> AnimalDataType.PORTAL_PROTECT
            "SIGHTING" -> AnimalDataType.MY_WITNESS
            else -> AnimalDataType.PORTAL_LOST
        }
    )

fun UserMyPostResponseDto.toMyPostModelList(): List<MyPostModel> =
    this.postAnimal?.map { it.toUiModel() } ?: emptyList()

fun AnimalDetailResponseDto.toMNAnimalDetail(): MNAnimalDetail =
    MNAnimalDetail(
        status = this.status,
        img = this.img,
        gender = this.gender,
        breed = this.breed,
        address = this.address,
        contact = this.contact,
        detail = this.detail,
        isScrapped = false // 기본값으로 설정, 추후 스크랩 상태 API에서 가져와야 함
    )

fun UserAnimalDetailResponseDto.toUserAnimalDetailUiModel(): UserAnimalDetailUiModel =
    UserAnimalDetailUiModel(
        animalType = when (this.animal) {
            "DOG" -> AnimalType.DOG
            "CAT" -> AnimalType.CAT
            else -> AnimalType.OTHER
        },
        breed = this.breed,
        gender = when (this.gender) {
            "MALE" -> Gender.MALE
            else -> Gender.FEMALE
        },
        age = this.age,
        detail = this.detail,
        imageUrl = this.img
    )

fun AlarmAnimalDto.toNotificationAnimalUiModel(): NotificationAnimalUiModel =
    NotificationAnimalUiModel(
        animalId = this.animalId,
        type = when (this.status) {
            "LOST" -> AnimalDataType.MN_LOST
            "PROTECTED" -> AnimalDataType.PORTAL_PROTECT
            "SIGHTING" -> AnimalDataType.MY_WITNESS
            else -> AnimalDataType.PORTAL_LOST
        },
        imageUrl = this.img,
        name = this.name,
        address = fromCode(this.city).name,
        gender = when (this.gender) {
            "FEMALE" -> Gender.FEMALE
            else -> Gender.MALE
        },
        date = this.occurredAt
    )

fun UserAlarmResponseDto.toNotificationAnimals(): List<NotificationAnimalUiModel> =
    this.animal?.map { it.toNotificationAnimalUiModel() } ?: emptyList()

