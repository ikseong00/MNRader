package com.example.mnrader.ui.mypage.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.example.mnrader.ui.mypage.repository.PetRepository
import java.io.File

class PetUploadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PetRepository()

    fun updatePet(
        animalId: Int,
        imageUri: Uri?,
        name: String,
        species: String,
        breed: String,
        gender: String,
        age: String,
        description: String?,
        onResult: (Boolean) -> Unit
    ) {
        val animalCode = when (species) {
            "강아지" -> 1
            "고양이" -> 2
            else -> 3
        }

        val genderCode = when (gender) {
            "수컷" -> 1
            "암컷" -> 2
            else -> 3
        }

        val parsedAge = age.filter { it.isDigit() }.toIntOrNull() ?: 0

        repository.updatePetData(
            animalId = animalId,
            imageUri = imageUri,
            name = name,
            animal = animalCode,
            breed = breed,
            gender = genderCode,
            age = parsedAge,
            detail = description,
            getFileFromUri = { uri -> uriToFile(uri) },
            onResult = onResult
        )
    }

    private fun uriToFile(uri: Uri): File? {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return file
    }
}
