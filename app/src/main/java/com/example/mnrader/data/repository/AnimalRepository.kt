package com.example.mnrader.data.repository

import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.dto.animal.request.AnimalRegisterRequestDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AnimalRepository() {
    private val animalService = RetrofitClient.animalService

    suspend fun getAnimalDetail(animalId: Long) = runCatching {
        animalService.getAnimalDetail(animalId)
    }
    
    suspend fun scrapAnimal(animalId: Int) = runCatching {
        animalService.scrapAnimal(animalId)
    }
    
    suspend fun unscrapAnimal(animalId: Int) = runCatching {
        animalService.unscrapAnimal(animalId)
    }
    
    suspend fun registerAnimal(
        requestDto: AnimalRegisterRequestDto,
        imageFile: File
    ) = runCatching {
        val statusPart = requestDto.status.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val namePart = requestDto.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val contactPart = requestDto.contact.toRequestBody("text/plain".toMediaTypeOrNull())
        val breedPart = requestDto.breed.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderPart = requestDto.gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val addressPart = requestDto.address.toRequestBody("text/plain".toMediaTypeOrNull())
        val occuredAtPart = requestDto.occuredAt.toRequestBody("text/plain".toMediaTypeOrNull())
        val detailPart = requestDto.detail.toRequestBody("text/plain".toMediaTypeOrNull())
        
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imgPart = MultipartBody.Part.createFormData("img", imageFile.name, requestFile)
        
        animalService.registerAnimal(
            status = statusPart,
            name = namePart,
            contact = contactPart,
            breed = breedPart,
            gender = genderPart,
            address = addressPart,
            occuredAt = occuredAtPart,
            detail = detailPart,
            img = imgPart
        )
    }
} 