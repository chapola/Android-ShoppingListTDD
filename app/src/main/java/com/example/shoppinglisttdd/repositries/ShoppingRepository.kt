package com.example.shoppinglisttdd.repositries

import androidx.lifecycle.LiveData
import com.example.shoppinglisttdd.data.local.ShoppingItem
import com.example.shoppinglisttdd.data.remote.response.ImageResponse
import com.example.shoppinglisttdd.util.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>
    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}