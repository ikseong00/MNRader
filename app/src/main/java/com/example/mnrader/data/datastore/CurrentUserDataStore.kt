package com.example.mnrader.data.datastore

import android.content.Context
import android.content.SharedPreferences

class CurrentUserDataStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "current_user_prefs", 
        Context.MODE_PRIVATE
    )

    fun setCurrentUser(email: String) {
        prefs.edit().putString(KEY_CURRENT_USER_EMAIL, email).apply()
    }

    fun getCurrentUser(): String? {
        return prefs.getString(KEY_CURRENT_USER_EMAIL, null)
    }

    fun clearCurrentUser() {
        prefs.edit().remove(KEY_CURRENT_USER_EMAIL).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    companion object {
        private const val KEY_CURRENT_USER_EMAIL = "current_user_email"
    }
} 