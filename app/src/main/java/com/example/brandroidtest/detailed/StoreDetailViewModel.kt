package com.example.brandroidtest.detailed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brandroidtest.model.Store

class StoreDetailViewModel(store: Store, application: Application) : AndroidViewModel(application) {



    private val _selectedStore = MutableLiveData<Store>()

    val selectedStore : LiveData<Store>
        get() = _selectedStore


    init {
        _selectedStore.value = store
    }

}