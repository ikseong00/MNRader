package com.example.mnrader.ui.mypage.repository

import android.net.Uri
import android.util.Log
import com.example.mnrader.ui.mypage.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.File

class PetRepository {

    fun updatePetData(
        animalId: Int,
        imageUri: Uri?,
        animal: Int,
        breed: String,
        gender: Int,
        age: Int,
        detail: String?,
        getFileFromUri: (Uri) -> File?,
        onResult: (Boolean) -> Unit
    ) {
        // image part
        val imgPart = imageUri?.let { uri ->
            getFileFromUri(uri)?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("img", file.name, requestFile)
            }
        }

        // request body parts
        val animalBody = animal.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedBody = breed.toRequestBody("text/plain".toMediaTypeOrNull())
        val genderBody = gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val ageBody = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val detailBody = (detail ?: "").toRequestBody("text/plain".toMediaTypeOrNull())

        val call = RetrofitClient.petService.updatePet(
            animalId = animalId,
            animal = animalBody,
            breed = breedBody,
            gender = genderBody,
            age = ageBody,
            detail = detailBody,
            img = imgPart
        )

        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("update", "성공적으로 업데이트 됨")
                    onResult(true)
                } else {
                    Log.e("update", "업데이트 실패: ${response.code()}")
                    onResult(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Log.e("update", "업데이트 오류: ${t.message}")
                onResult(false)
            }
        })
    }

    // 설정화면에서 동물 추가 작업
    fun addPetData(
        imageUri: Uri?,
        animal: Int,
        breed: String,
        gender: Int,
        name: String,
        age: Int,
        detail: String,
        status: Int, // 추가
        getFileFromUri: (Uri) -> File?,
        onResult: (Boolean) -> Unit
    ) {
        val imgPart = imageUri?.let { uri ->
            getFileFromUri(uri)?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("img", file.name, requestFile)
            }
        }

        val animalBody = animal.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedBody = breed.toRequestBody("text/plain".toMediaTypeOrNull())
        val genderBody = gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val ageBody = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        // detail에 아무 입력 안했을때 에러 방지
        val detailBody = (detail.ifEmpty { " " }).toRequestBody("text/plain".toMediaTypeOrNull())
        // status 안보내서 에러 발생
        val statusBody = status.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        val call = RetrofitClient.petService.addPet(
            animal = animalBody,
            breed = breedBody,
            gender = genderBody,
            name = nameBody,
            age = ageBody,
            detail = detailBody,
            status = statusBody, // 추가
            img = imgPart
        )

        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("addPet", "동물 추가 성공")
                    onResult(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("addPet", "동물 추가 실패: code=${response.code()} body=$errorBody")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("addPet", "네트워크 실패: ${t.message}")
                onResult(false)
            }
        })

    }

}
