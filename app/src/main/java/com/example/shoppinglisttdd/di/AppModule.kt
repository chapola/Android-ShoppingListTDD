package com.example.shoppinglisttdd.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.example.shoppinglisttdd.data.local.ShoppingDao
import com.example.shoppinglisttdd.data.local.ShoppingItemsDatabase
import com.example.shoppinglisttdd.data.remote.PixabayAPI
import com.example.shoppinglisttdd.repositries.DefaultShoppingRepository
import com.example.shoppinglisttdd.repositries.ShoppingRepository
import com.example.shoppinglisttdd.util.Constants.BASE_URL
import com.example.shoppinglisttdd.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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