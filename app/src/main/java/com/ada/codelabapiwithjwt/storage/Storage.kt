package com.ada.codelabapiwithjwt.storage

interface Storage {

    fun saveToken(token: String)

    fun getToken(): String?

    fun isAuthenticated(): Boolean?

    fun clearToken()
}