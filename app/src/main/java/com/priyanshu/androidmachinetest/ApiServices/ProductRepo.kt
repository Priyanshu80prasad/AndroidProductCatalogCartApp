package com.priyanshu.androidmachinetest.ApiServices

import com.priyanshu.androidmachinetest.product.Category
import com.priyanshu.androidmachinetest.product.ProductResponse

class ProductRepo (
private val api : ProductApi
){
    suspend fun getProducts() : ProductResponse{
        return api.getProducts()
    }
    suspend fun searchProducts(query : String): ProductResponse {
        return api.searchProducts(query)
    }
    suspend fun getCategories() : List<Category> {
        return api.getCategories()
    }
    suspend fun getProductCategory(slug : String) : ProductResponse{
        return api.getProductCategory(slug)
    }
}
