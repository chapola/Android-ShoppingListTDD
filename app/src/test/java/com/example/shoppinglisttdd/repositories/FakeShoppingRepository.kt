package com.example.shoppinglisttdd.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglisttdd.data.local.ShoppingItems
import com.example.shoppinglisttdd.data.remote.response.ImageResponse
import com.example.shoppinglisttdd.repositries.ShoppingRepository
import com.example.shoppinglisttdd.util.Resource

class FakeShoppingRepository: ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItems>()
    private val observableAllShoppingItems = MutableLiveData<List<ShoppingItems>>()
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }
    private fun refreshLiveData(){
        observableAllShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float? {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItems) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItems) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItems>> {
        return observableAllShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
       return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error",null)
        }else{
            Resource.success(ImageResponse(0,0, listOf()))
        }
    }
}