package com.android.shoppinglisttdd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.shoppinglisttdd.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemsDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItems(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }
    @Test
    fun deleteShoppingItems() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItems(shoppingItem)
        dao.deleteShoppingItems(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItem)

    }
    @Test
    fun observeTotalPrice() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 5, 100f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 2, 10f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 10, 123f, "url", id = 3)
        val shoppingItem4 = ShoppingItem("name", 15, 134f, "url", id = 4)

        dao.insertShoppingItems(shoppingItem1)
        dao.insertShoppingItems(shoppingItem2)
        dao.insertShoppingItems(shoppingItem3)
        dao.insertShoppingItems(shoppingItem4)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(5*100+2*10+10*123+15*134)

    }

}