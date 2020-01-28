package com.example.brandroidtest.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.brandroidtest.databinding.FragmentStoreBinding


class StoreFragment : Fragment() {



    //Will Create a ViewModelProivders object of class StoreViewModel the first time viewModel is used
    //Allows us to move this code from on create to the declaration
    private val viewModel: StoreViewModel by lazy {
        val factory = StoreViewModelFactory(requireNotNull(activity).application)
        ViewModelProviders.of(this, factory).get(StoreViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.i("onCreateView", "StoreFragment created")


        val binding = FragmentStoreBinding.inflate(inflater)



        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.storeList.adapter = StoreAdapter(StoreAdapter.OnClickListener {
            viewModel.displayStoreDetails(it)
            Log.i("inside OnClickListener", "after displayDetails")
        })
//        binding.storeList.adapter = StoreAdapter(object: StoreAdapter.OnClickListener {
//            override fun onClick(store: Store) {
//                viewModel.displayStoreDetails(store)
//                Log.i("inside OnClickListener", "after displayDetails")
//            }
//        })

        Log.i("between adapter.onclick", "and viewModel observe")

        viewModel.selectedStore.observe(this, Observer {
            Log.i("observe", "inside the selectedStore observe method")
            if (null != it) {
                this.findNavController().navigate(
                    StoreFragmentDirections.actionMainListFragmentToDetailFragment(it)
                )
                viewModel.displayStoreDetailsComplete()
            }

        })



        return binding.root

    }

}
