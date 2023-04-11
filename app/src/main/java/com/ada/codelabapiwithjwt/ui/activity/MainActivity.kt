package com.ada.codelabapiwithjwt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ada.codelabapiwithjwt.R
import com.ada.codelabapiwithjwt.data.AppDatabase
import com.ada.codelabapiwithjwt.data.dao.ProductDao
import com.ada.codelabapiwithjwt.data.entity.Product
import com.ada.codelabapiwithjwt.network.dto.product.ProductDto
import com.ada.codelabapiwithjwt.network.service.ProductsService
import com.ada.codelabapiwithjwt.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var storage: Storage

    @Inject
    lateinit var productsService: ProductsService

    /*@Inject
    lateinit var db: AppDatabase*/

    @Inject
    lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("AndroidKotlinAda", "Authenticated user with the JWT ${storage.getToken()}")

        val idProduct = "63db215f98077f6f4162ad1a"

        requestProductData(idProduct)

    }

    private fun requestProductData(idProduct: String) {
        GlobalScope.launch {
            val response = productsService.getProducts(idProduct)
            if (response.isSuccessful){
                Log.d("AndroidKotlinAda", "Response product with id $idProduct = ${response.body()}")
                val responseProduct: ProductDto? = response.body()
                saveProduct(responseProduct)
            }
        }
    }

    private fun saveProduct(productDto: ProductDto?){
        GlobalScope.launch {
            val product = Product(
                productDto?.category,
                productDto?.description,
                productDto?.imageUrl,
                productDto?.name,
                productDto?.price
            )

            productDao.insertAllProducts(product)
            Log.d("AndroidKotlinAda", "Product saved with success")
        }
    }
}