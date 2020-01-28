package com.example.brandroidtest.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brandroidtest.model.Store

@Database(entities = arrayOf(Store::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao?

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "stores-database"
        private var databaseInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (databaseInstance == null) {
                synchronized(LOCK) {
                    Log.d(
                        LOG_TAG,
                        "Creating new Database Instance "
                    )
                    databaseInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            Log.d(LOG_TAG, "Getting the Database Instance ")
            return databaseInstance
        }
    }
}