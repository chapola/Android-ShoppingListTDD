package com.example.shoppinglisttdd.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItems(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items_table")
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items_table")
    fun observeTotalPrice():LiveData<Float>


}