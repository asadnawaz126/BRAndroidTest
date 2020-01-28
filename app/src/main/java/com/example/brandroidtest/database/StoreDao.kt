package com.example.brandroidtest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.brandroidtest.model.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM store")
    fun getAll(): List<Store>


    @Insert
    fun insertAllDataIntoDatabase(storesList: List<Store>)

    @Query("DELETE FROM store")
    fun deleteAllDatabaseData()
}