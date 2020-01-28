package com.example.brandroidtest.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brandroidtest.model.Store
import com.example.brandroidtest.databinding.ListItemBinding


class StoreAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Store, StoreAdapter.StoreViewHolder>(DiffCallback) {


    class StoreViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.store = store
            Log.i("Adapter bind", store.storeLogoURL)
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Store>() {
        override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem.storeID == newItem.storeID
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreViewHolder {
        return StoreViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {

        val store = getItem(position)

        Log.i("inside onBindViewHolder", "")

        holder.itemView.setOnClickListener {
            Log.i("inside onBindViewHolder", "setOnClickListener")
            onClickListener.onClick(store)
        }

        holder.bind(store)
    }

    class OnClickListener(val clickListener: (store: Store) -> Unit) {

        fun onClick(store: Store) {
            Log.i("inside onClick", "click is being registered ${store.city}")
            return clickListener(store)
        }
    }

//    interface OnClickListener {
//        fun onClick(store: Store)
//    }

}