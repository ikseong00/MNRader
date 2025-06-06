package com.example.mnrader.ui.mypage.repository

import android.net.Uri
import android.util.Log
import com.example.mnrader.ui.mypage.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
        val imgPart = imageUri?.let { uri ->
            getFileFromUri(uri)?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("img", file.name, requestFile)
            }
        }

        val animalPart = animal.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedPart = breed.toRequestBody("text/plain".toMediaTypeOrNull())
        val genderPart = gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val agePart = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val detailPart = (detail ?: "").toRequestBody("text/plain".toMediaTypeOrNull())

        val call = RetrofitClient.petService.updatePet(
            animalId = animalId,
            img = imgPart,
            animal = animalPart,
            breed = breedPart,
            gender = genderPart,
            age = agePart,
            detail = detailPart
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
}
