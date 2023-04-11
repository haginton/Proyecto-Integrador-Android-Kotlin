package com.ada.codelabapiwithjwt.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ada.codelabapiwithjwt.data.entity.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product WHERE product_id IN (:productIds)")
    fun loadAllProductsByIds(productIds: IntArray): List<Product>

    @Query("SELECT * FROM product WHERE name LIKE :nameProduct LIMIT 1")
    fun findByNameProduct(nameProduct: String): Product

    @Insert
    fun insertAllProducts(vararg product: Product)

    @Delete
    fun deleteProduct(product: Product)
}