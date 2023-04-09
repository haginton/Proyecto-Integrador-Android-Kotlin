package com.ada.codelabapiwithjwt.network.service

import com.ada.codelabapiwithjwt.network.dto.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {
    @GET("v1/products/{id}")
    suspend fun getProducts(@Path("id") id: String): Response<ProductDto>
}