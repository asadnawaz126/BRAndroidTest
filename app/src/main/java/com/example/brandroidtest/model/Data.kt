package com.example.brandroidtest.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


/**
 * Data is the main name of this class because there was no name for the main JSON Object of which stores was a sub-object of.
 * Therefore Data houses a List<Store> member that would hold all the actual stores
 */


data class Data(
    @field:Json(name = "stores") val stores: List<Store>
)

@Entity
@Parcelize
data class Store(
    val address: String,
    val city: String,
    val name: String,
    val latitude: String,
    val zipcode: String,
    @Json(name="storeLogoURL") val storeLogoURL: String,
    val phone: String,
    val longitude: String,
    @PrimaryKey
    val storeID: String,
    val state: String
) : Parcelable
