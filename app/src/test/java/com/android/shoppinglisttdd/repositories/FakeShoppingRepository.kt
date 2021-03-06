package com.android.shoppinglisttdd.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.shoppinglisttdd.data.local.ShoppingItem
import com.android.shoppinglisttdd.data.remote.response.ImageResponse
import com.android.shoppinglisttdd.repositries.ShoppingRepository
import com.android.shoppinglisttdd.util.Resource

class FakeShoppingRepository: ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableAllShoppingItems = MutableLiveData<List<ShoppingItem>>()
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

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
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