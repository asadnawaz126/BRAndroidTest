package com.example.brandroidtest.detailed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.brandroidtest.databinding.FragmentStoreDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class StoreDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application

        val binding = FragmentStoreDetailBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        val store = StoreDetailFragmentArgs.fromBundle(arguments!!).selectedStore

        val viewModelFactory = StoreDetailViewModelFactory(store, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreDetailViewModel::class.java)

        return binding.root

    }
}

