package com.priyanshu.androidmachinetest.ApiServices

import com.priyanshu.androidmachinetest.product.Products
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRetrofit {
    val productServices : ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

}