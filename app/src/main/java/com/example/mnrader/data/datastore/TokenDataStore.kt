package com.example.mnrader.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_preferences")

class TokenDataStore(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    // 액세스 토큰 가져오기 (suspend function)
    suspend fun getAccessToken(): String {
        return context.dataStore.data.first()[ACCESS_TOKEN_KEY] ?: ""
    }

    // 리프레시 토큰 가져오기 (suspend function)
    suspend fun getRefreshToken(): String {
        return context.dataStore.data.first()[REFRESH_TOKEN_KEY] ?: ""
    }

    // 로그인 상태 확인
    suspend fun isLoggedIn(): Boolean {
        val accessToken = getAccessToken()
        return accessToken.isNotEmpty()
    }
} 