package com.ada.codelabapiwithjwt.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ada.codelabapiwithjwt.network.dto.auth.LoginDto
import com.ada.codelabapiwithjwt.network.dto.auth.TokenDto
import com.ada.codelabapiwithjwt.network.service.AuthService
import com.ada.codelabapiwithjwt.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth()
    }

    private fun auth() {
        GlobalScope.launch {
            val loginDto = LoginDto(
                "ada6@mail.com",
                "1523asd*"
            )

            val response: Response<TokenDto> = authService.login(loginDto)
            if (response.isSuccessful){
                val token: TokenDto? = response.body()
                Log.d("AndroidKotlinAda", "Login success with the token: ${token?.token}")
                storage.saveToken(token!!.token)
                Log.d("AndroidKotlinAda", "shared preferences saved ok with the token from shared preferences: ${storage.getToken()}")
            }else {
                //codigo de respuesta si hay error en la autenticación para generar el JWT
            }
        }
    }

}