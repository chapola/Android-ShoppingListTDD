package com.example.shoppinglisttdd.repositries

import androidx.lifecycle.LiveData
import com.example.shoppinglisttdd.data.local.ShoppingDao
import com.example.shoppinglisttdd.data.local.ShoppingItem
import com.example.shoppinglisttdd.data.remote.PixabayAPI
import com.example.shoppinglisttdd.data.remote.response.ImageResponse
import com.example.shoppinglisttdd.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItems(shoppingItem)
    }
    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItems(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
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