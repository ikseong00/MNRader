package com.example.mnrader.data.repository

import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.dto.user.request.UpdateUserAnimalRequestDto
import com.example.mnrader.data.dto.user.request.UserAnimalRequestDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserAnimalRepository() {
    private val service = RetrofitClient.userAnimalService

    suspend fun createUserAnimal(
        requestDto: UserAnimalRequestDto,
        imageFile: File
    ) = runCatching {
        val animalPart = requestDto.animal.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedPart = requestDto.breed.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderPart = requestDto.gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val namePart = requestDto.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val agePart = requestDto.age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val detailPart = requestDto.detail.toRequestBody("text/plain".toMediaTypeOrNull())
        
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imgPart = MultipartBody.Part.createFormData("img", imageFile.name, requestFile)
        
        service.createUserAnimal(
            animal = animalPart,
            breed = breedPart,
            gender = genderPart,
            name = namePart,
            age = agePart,
            detail = detailPart,
            img = imgPart
        )
    }

    suspend fun updateUserAnimal(
        animalId: Int,
        requestDto: UpdateUserAnimalRequestDto,
        imageFile: File
    ) = runCatching {
        val animalPart = requestDto.animal.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedPart = requestDto.breed.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderPart = requestDto.gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val agePart = requestDto.age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val detailPart = requestDto.detail.toRequestBody("text/plain".toMediaTypeOrNull())
        
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imgPart = MultipartBody.Part.createFormData("img", imageFile.name, requestFile)
        
        service.updateUserAnimal(
            animalId = animalId,
            animal = animalPart,
            breed = breedPart,
            gender = genderPart,
            age = agePart,
            detail = detailPart,
            img = imgPart
        )
    }

    suspend fun getUserAnimalDetail(animalId: Int) = runCatching {
        service.getUserAnimalDetail(animalId)
    }
} 