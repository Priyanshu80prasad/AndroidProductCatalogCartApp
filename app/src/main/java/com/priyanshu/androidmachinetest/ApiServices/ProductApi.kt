package com.priyanshu.androidmachinetest.ApiServices

import com.priyanshu.androidmachinetest.product.Category
import com.priyanshu.androidmachinetest.product.ProductResponse
import com.priyanshu.androidmachinetest.product.Products
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(@Query("limit")limit: Int=30) : ProductResponse

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String) : ProductResponse

    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("product/category/{slug}")
    suspend fun getProductCategory(
        @Path("slug")slug : String
    ): ProductResponse


}