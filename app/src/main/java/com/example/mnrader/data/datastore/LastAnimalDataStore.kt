package com.example.mnrader.data.datastore

import android.content.Context
import android.content.SharedPreferences

class LastAnimalDataStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "last_animal_prefs"
        private const val KEY_LAST_ANIMAL = "last_animal"
    }

    fun saveLastAnimal(lastAnimal: Int) {
        prefs.edit()
            .putInt(KEY_LAST_ANIMAL, lastAnimal)
            .apply()
    }

    fun getLastAnimal(): Int {
        return prefs.getInt(KEY_LAST_ANIMAL, 0)
    }

    fun clearLastAnimal() {
        prefs.edit()
            .remove(KEY_LAST_ANIMAL)
            .apply()
    }
} 