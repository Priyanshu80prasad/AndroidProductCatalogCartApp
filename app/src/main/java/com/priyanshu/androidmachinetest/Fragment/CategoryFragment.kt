package com.priyanshu.androidmachinetest.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshu.androidmachinetest.Adapter.CategoryAdapter
import com.priyanshu.androidmachinetest.databinding.CategoryFragmentBinding
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModel
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModelFactory

class CategoryFragment : Fragment() {
    private lateinit var binding: CategoryFragmentBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(
            requireActivity()   ,
            ProductViewModelFactory()
        )[ProductViewModel::class.java]

        binding.categoryRecycle.layoutManager = LinearLayoutManager(requireContext())
        productViewModel.categoryLiveData.observe(viewLifecycleOwner){ categories ->

            binding.categoryRecycle.adapter = CategoryAdapter(categories) { category ->
                android.util.Log.d("SELECT_CATEGORY",category.slug)
                productViewModel.getProductCategory(category.slug)
                parentFragmentManager.popBackStack()
            }
        }
        productViewModel.fetchCategories()
    }
}