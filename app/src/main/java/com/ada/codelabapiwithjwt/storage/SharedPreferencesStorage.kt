package com.ada.codelabapiwithjwt.storage

import android.content.SharedPreferences
import com.ada.codelabapiwithjwt.TOKEN_KEY

class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences): Storage {
    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    override fun isAuthenticated(): Boolean? {
        return getToken()?.isNotEmpty()
    }

    override fun clearToken() {
        sharedPreferences.edit().clear().apply()
    }
}