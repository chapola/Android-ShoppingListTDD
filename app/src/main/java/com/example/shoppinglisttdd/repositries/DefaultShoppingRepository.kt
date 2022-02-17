package com.example.shoppinglisttdd.repositries

import androidx.lifecycle.LiveData
import com.example.shoppinglisttdd.data.local.ShoppingDao
import com.example.shoppinglisttdd.data.local.ShoppingItems
import com.example.shoppinglisttdd.data.remote.PixabayAPI
import com.example.shoppinglisttdd.data.remote.response.ImageResponse
import com.example.shoppinglisttdd.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItems: ShoppingItems) {
        shoppingDao.insertShoppingItems(shoppingItems)
    }
    override suspend fun deleteShoppingItem(shoppingItems: ShoppingItems) {
        shoppingDao.deleteShoppingItems(shoppingItems)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItems>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error ", null)
            }else{
                Resource.error("An unknown error", null)
            }

        }catch (e: Exception){
            Resource.error(e.message.toString(),null)
        }

    }

}