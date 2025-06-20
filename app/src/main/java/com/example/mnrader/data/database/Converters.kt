package com.example.mnrader.data.database

import androidx.room.TypeConverter
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.Gender

class Converters {
    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }

    @TypeConverter
    fun toGender(genderString: String): Gender {
        return Gender.valueOf(genderString)
    }

    @TypeConverter
    fun fromAnimalDataType(type: AnimalDataType): String {
        return type.name
    }

    @TypeConverter
    fun toAnimalDataType(typeString: String): AnimalDataType {
        return AnimalDataType.valueOf(typeString)
    }
} 