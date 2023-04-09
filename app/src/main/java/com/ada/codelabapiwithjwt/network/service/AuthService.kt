package com.ada.codelabapiwithjwt.network.service

import com.ada.codelabapiwithjwt.network.dto.auth.LoginDto
import com.ada.codelabapiwithjwt.network.dto.auth.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>
}