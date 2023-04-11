package com.ada.codelabapiwithjwt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ada.codelabapiwithjwt.data.dao.ProductDao
import com.ada.codelabapiwithjwt.data.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}