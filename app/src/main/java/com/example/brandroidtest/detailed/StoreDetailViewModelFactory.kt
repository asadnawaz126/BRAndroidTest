package com.example.brandroidtest.detailed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brandroidtest.model.Store

class StoreDetailViewModelFactory(
    private val store: Store,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreDetailViewModel::class.java)) {
            return StoreDetailViewModel(store, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}