package com.priyanshu.androidmachinetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.priyanshu.androidmachinetest.ApiServices.ProductRepo
import com.priyanshu.androidmachinetest.ApiServices.ProductRetrofit
import retrofit2.Retrofit

class ProductViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            val repository = ProductRepo(
                ProductRetrofit.productServices

            )
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}