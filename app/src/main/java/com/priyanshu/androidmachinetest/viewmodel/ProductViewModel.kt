package com.priyanshu.androidmachinetest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.priyanshu.androidmachinetest.ApiServices.ProductRepo
import com.priyanshu.androidmachinetest.product.Category
import com.priyanshu.androidmachinetest.product.Products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val repository : ProductRepo
): ViewModel() {
    val productLiveData = MutableLiveData<List<Products>>()
    val categoryLiveData = MutableLiveData<List<Category>>()
    val selectedProductLiveData = MutableLiveData<Products>()
    val cartData = MutableLiveData<MutableList<Products>>(mutableListOf())
    var isCategorySelected = false

    fun fetchProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isCategorySelected = false
                val response = repository.getProducts()
                withContext(Dispatchers.Main){
                    productLiveData.postValue(response.products)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun fetchCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getCategories()
                withContext(Dispatchers.Main){
                    categoryLiveData.postValue(response)

                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
    fun getProductCategory(slug : String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isCategorySelected = true
                val response = repository.getProductCategory(slug)
                withContext(Dispatchers.Main) {
                    productLiveData.postValue(response.products)
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }

    }
    fun selectProduct(products: Products) {
        selectedProductLiveData.postValue(products)
    }
    fun searchProducts(query : String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.searchProducts(query)
                withContext(Dispatchers.Main) {
                    productLiveData.postValue(response.products)
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
    fun addToCart(products: Products) {
        val cart = cartData.value ?: mutableListOf()
        cart.add(products)
        cartData.postValue(cart)
    }
    fun removeFromCart(products: Products) {
        val cart = cartData.value ?: mutableListOf()
        cart.remove(products)
        cartData.postValue(cart)
    }

    fun clearCart() {
        cartData.postValue(mutableListOf())
    }

}