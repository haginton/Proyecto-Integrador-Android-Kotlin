package com.ada.codelabapiwithjwt.di

import android.content.Context
import androidx.room.Room
import com.ada.codelabapiwithjwt.SHARED_PREFERENCES_FILE_NAME
import com.ada.codelabapiwithjwt.data.AppDatabase
import com.ada.codelabapiwithjwt.data.dao.ProductDao
import com.ada.codelabapiwithjwt.network.JWTInterceptor
import com.ada.codelabapiwithjwt.network.service.AuthService
import com.ada.codelabapiwithjwt.network.service.ProductsService
import com.ada.codelabapiwithjwt.storage.SharedPreferencesStorage
import com.ada.codelabapiwithjwt.storage.Storage
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideProductDao( appDatabase: AppDatabase): ProductDao{
        return appDatabase.productDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductsService{
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    fun provideStorage(@ApplicationContext appContext: Context): Storage{
        val sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesStorage(sharedPreferences)
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun providesRetrofit(storage: Storage): Retrofit{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(JWTInterceptor(storage))
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        return Retrofit.Builder()
            //.baseUrl("https://www.omdbapi.com/")
            .baseUrl("https://api-rest-java-production-bd09.up.railway.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}