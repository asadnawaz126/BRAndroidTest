package com.example.brandroidtest.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brandroidtest.database.AppDatabase
import com.example.brandroidtest.model.Store
import com.example.brandroidtest.network.StoreAPI
import kotlinx.coroutines.*


enum class StoreAPIStatus {LOADING, DONE, NO_CONNECTION}

class StoreViewModel(application: Application) : AndroidViewModel(application) {


    private val db = AppDatabase.getInstance(application.applicationContext)


    // Response from server: Either Store Data or Failure Message
    private val _status = MutableLiveData<StoreAPIStatus>()

    // for status of get request
    //displayed when there is no internet connection or if the connection is unstable and the data is being loaded
    val status: LiveData<StoreAPIStatus>
        get() = _status


    //internal variable accessed within this file
    private val listOfStores = MutableLiveData<List<Store>>()


    //external variable for anywhere else
    val stores: LiveData<List<Store>>
        get() = listOfStores


    private val _selectedStore = MutableLiveData<Store>()

    val selectedStore: LiveData<Store>
        get() = _selectedStore


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Call getStoreData() in init so we can display the result immediately.
     */
    init {


        Log.i("viewModel init", "inside StoreViewModel init block")
        if (isNetworkConnected(application.applicationContext)){

            Log.i("init storeviewmodel", " internet available")
            if(null != db?.storeDao()?.getAll())
                db?.storeDao()?.deleteAllDatabaseData()

            getStoreData()
        }

        else{
            if(emptyList<Store>() != db?.storeDao()?.getAll()){
                Log.i("init storeviewmodel", " no internet but database entries available")
                listOfStores.value = db?.storeDao()?.getAll()
            }

            else{

                Log.i("init storeviewmodel", " no internet and no database entries")
                listOfStores.value = emptyList()

                _status.value = StoreAPIStatus.NO_CONNECTION
            }
        }

    }
    /**
     * Sets the value of the status LiveData to the Store API data.
     */

    private fun getStoreData() {

        Log.i("getStoreData()", " inside getStoreData")


        coroutineScope.launch {
            try {
                Log.i("getStoreData()", "Inside the coroutine before getData")

               _status.value =  StoreAPIStatus.LOADING

                var storeData = async { StoreAPI.retrofitService.getData().stores }.await()

                Log.i("getStoreData()", "Inside the coroutine after getData")

                _status.value = StoreAPIStatus.DONE

                listOfStores.value = storeData


                db?.storeDao()?.insertAllDataIntoDatabase(storeData)

            } catch (e: Exception) {
                _status.value = StoreAPIStatus.NO_CONNECTION
                listOfStores.value = ArrayList()
                e.printStackTrace()
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    //will be called to set the store as the one that was clicked
    fun displayStoreDetails(store : Store){
        Log.i("displayStoreDetails", "inside this method")
        _selectedStore.value = store
    }

    //sets the selected store's value to null so that live data can be updated when we select a new store and not show us the detail apge of the same store
    fun displayStoreDetailsComplete() {
        Log.i("displayStoreDetailsComplete", "inside this method")
        _selectedStore.value = null
    }

}