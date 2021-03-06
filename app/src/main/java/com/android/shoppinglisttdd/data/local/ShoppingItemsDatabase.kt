package com.android.shoppinglisttdd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1,exportSchema = false
)
abstract class ShoppingItemsDatabase : RoomDatabase(){
    abstract fun shoppingDao(): ShoppingDao
}