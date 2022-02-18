package com.android.shoppinglisttdd.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shoppinglisttdd.data.local.ShoppingItem
import com.android.shoppinglisttdd.data.remote.response.ImageResponse
import com.android.shoppinglisttdd.repositries.ShoppingRepository
import com.android.shoppinglisttdd.util.Constants
import com.android.shoppinglisttdd.util.Event
import com.android.shoppinglisttdd.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository

) : ViewModel() {

    // get shopping items
    val shoppingItems = repository.observeAllShoppingItem()

    // get total price
    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images : LiveData<Event<Resource<ImageResponse>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: MutableLiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: MutableLiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus


    fun setCurrentImageUrl(url: String){
        _currentImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemToDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String){
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Field empty!!!!", null)))
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Name length exceed!!!", null)))
            return
        }
        if (priceString.length> Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Price length  exceed", null)))
            return
        }

        val amount = try {
            amountString.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItem(name, amount, priceString.toFloat(), _currentImageUrl.value?:"")
        insertShoppingItemToDB(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))


    }

    fun searchForImage(imageQuery: String){
        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(data = null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }

    }


}