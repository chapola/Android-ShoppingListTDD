package com.example.shoppinglisttdd.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(shoppingItems: ShoppingItems)

    @Delete
    suspend fun deleteShoppingItems(shoppingItems: ShoppingItems)

    @Query("SELECT * FROM shopping_items_table")
    fun observeAllShoppingItems():LiveData<List<ShoppingItems>>

    @Query("SELECT SUM(price * amount) FROM shopping_items_table")
    fun observeTotalPrice():LiveData<Float>


}