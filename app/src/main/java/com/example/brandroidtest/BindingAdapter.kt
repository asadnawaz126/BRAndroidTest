package com.example.brandroidtest.glide

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.brandroidtest.model.Store
import com.example.brandroidtest.R
import com.example.brandroidtest.main.StoreAPIStatus
import com.example.brandroidtest.main.StoreAdapter

//This is a binding adapter for the recyclerview that will automatically update the recyclerview when the store data changes as the store data is of type LiveData
//inside the model. If no data, it will not show the recyclerview
@BindingAdapter("ListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Store>?) {
    val adapter = recyclerView.adapter as StoreAdapter
    adapter.submitList(data)

}


//Binding Adapter for loading images to the image views class via Glide library
@BindingAdapter("imageURL")
fun bindImage(imgView: ImageView, imgURL: String?) {
    Log.i("imgURL", imgURL)
    imgURL?.let {
        val imgUri = it.toUri().buildUpon().build()
        Log.i("Bind Image", imgUri.toString())
        Glide.with(imgView.context)
            .load(imgUri)

            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}


//This BindingAdapater binds an ImageView with the "storeAPIStatus" attribute with the status from the StoreViewModel which changes depending on the network connection
@BindingAdapter("storeAPIStatus")
fun bindStatus(statusImageView: ImageView, status: StoreAPIStatus?) {
    when (status) {
        StoreAPIStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        StoreAPIStatus.NO_CONNECTION -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        StoreAPIStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}


