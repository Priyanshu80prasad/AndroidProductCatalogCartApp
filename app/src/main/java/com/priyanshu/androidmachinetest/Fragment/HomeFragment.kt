package com.priyanshu.androidmachinetest.Fragment

import android.R.attr.query
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshu.androidmachinetest.Adapter.ProductAdapter
import com.priyanshu.androidmachinetest.ApiServices.ProductRetrofit
import com.priyanshu.androidmachinetest.R
import com.priyanshu.androidmachinetest.databinding.HomeFragmentBinding
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModel
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.internal.cache.DiskLruCache

class HomeFragment : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(
            inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProvider( requireActivity(),
            ProductViewModelFactory())[ProductViewModel::class.java]

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (productViewModel.isCategorySelected) {
                        productViewModel.fetchProduct()
                    } else {
                        isEnabled = true
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )

        binding.productRecycle.layoutManager = LinearLayoutManager(requireContext())

        productViewModel.productLiveData.observe(viewLifecycleOwner) { products ->
            binding.productRecycle.adapter = ProductAdapter(products){ products ->
                productViewModel.selectProduct(products)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ProductDetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        if (productViewModel.productLiveData.value == null){
            productViewModel.fetchProduct()
        }

        //search product
        binding.etSearch.setOnClickListener {
            val search = binding.etSearch.text.toString()
            if (search.isNotEmpty()) {
                productViewModel.searchProducts(search)
            } else {
                productViewModel.fetchProduct()

            }
        }

        //search category
        binding.btnCategory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CategoryFragment())
                .addToBackStack(null)
                .commit()
        }

        //cart click
        binding.btnCart.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

    }



}