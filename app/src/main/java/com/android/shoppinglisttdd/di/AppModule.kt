package com.android.shoppinglisttdd.di

import android.content.Context
import androidx.room.Room
import com.android.shoppinglisttdd.data.local.ShoppingDao
import com.android.shoppinglisttdd.data.local.ShoppingItemsDatabase
import com.android.shoppinglisttdd.data.remote.PixabayAPI
import com.android.shoppinglisttdd.repositries.DefaultShoppingRepository
import com.android.shoppinglisttdd.repositries.ShoppingRepository
import com.android.shoppinglisttdd.util.Constants.BASE_URL
import com.android.shoppinglisttdd.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ShoppingItemsDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao,api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemsDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayAPI():PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}