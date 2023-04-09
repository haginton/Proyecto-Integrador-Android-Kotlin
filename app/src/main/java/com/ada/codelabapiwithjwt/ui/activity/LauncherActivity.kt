package com.ada.codelabapiwithjwt.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ada.codelabapiwithjwt.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity: AppCompatActivity() {

    @Inject
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (storage.isAuthenticated()!!){
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                Log.d("AndroidKotlinAda", "The activity MainActivity will open, because the JWT is not empty: ${storage.getToken()}")
                finish()
            }
        }else {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                Log.d("AndroidKotlinAda", "The activity LoginActivity will open, because the JWT is empty")
                finish()
            }
        }
    }

}