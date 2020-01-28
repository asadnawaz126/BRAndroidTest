package com.example.brandroidtest.network


import com.example.brandroidtest.model.Data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val URL = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()

interface StoreAPIService{

    //Initially was using Jake Wharton's library for retrofit2 kotlin coroutines support but it has been deprecated since the support
    // addition of the suspend keyword in retrofit 2.6.0
    //Suspend does all the task of coroutines for us by just adding it before the function declaration

    @GET("stores.json")
    suspend fun getData():
            Data //return Data object because there is an unnamed parent JSON. Data has access to the Store JSON Object/Array
}

object StoreAPI{
    val retrofitService: StoreAPIService by lazy {
        retrofit.create(StoreAPIService::class.java)
    }
}