package com.android.shoppinglisttdd.repositries

import androidx.lifecycle.LiveData
import com.android.shoppinglisttdd.data.local.ShoppingItem
import com.android.shoppinglisttdd.data.remote.response.ImageResponse
import com.android.shoppinglisttdd.util.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>
    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}