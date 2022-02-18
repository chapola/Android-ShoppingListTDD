package com.example.shoppinglisttdd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttdd.MainCoroutineRule
import com.example.shoppinglisttdd.getOrAwaitValueTest
import com.example.shoppinglisttdd.repositories.FakeShoppingRepository
import com.example.shoppinglisttdd.util.Constants
import com.example.shoppinglisttdd.util.Status
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest{

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, return error`(){
        viewModel.insertShoppingItem("name","","3.0")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with tool long name, return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1){
                append("1")
            }
        }
        viewModel.insertShoppingItem(string,"5","3.0")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with tool long price, return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1){
                append(1)
            }
        }
        viewModel.insertShoppingItem("name","5",string)
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with tool high amount, return error`(){

        viewModel.insertShoppingItem("name","999999999999999999999999","3")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, return success`(){

        viewModel.insertShoppingItem("name","99999","3")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


}