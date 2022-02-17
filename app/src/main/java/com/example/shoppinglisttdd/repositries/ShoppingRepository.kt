package com.example.shoppinglisttdd.repositries

import androidx.lifecycle.LiveData
import com.example.shoppinglisttdd.data.local.ShoppingItems
import com.example.shoppinglisttdd.data.remote.response.ImageResponse
import com.example.shoppinglisttdd.util.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItems: ShoppingItems)

    suspend fun deleteShoppingItem(shoppingItems: ShoppingItems)

    fun observeAllShoppingItem():LiveData<List<ShoppingItems>>
    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}