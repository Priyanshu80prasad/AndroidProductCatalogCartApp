package com.priyanshu.androidmachinetest.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.priyanshu.androidmachinetest.databinding.ProductDetailFragmentBinding
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModel
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModelFactory

class ProductDetailsFragment : Fragment() {
    private lateinit var binding : ProductDetailFragmentBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(
               requireActivity(), ProductViewModelFactory())[ProductViewModel::class.java]

        productViewModel.selectedProductLiveData.observe(viewLifecycleOwner) { products ->
            binding.detailTitle.text = products.title
            binding.detailBrand.text = "Brand: ${products   .brand}"
            binding.detailCategory.text = "Category: ${products.category}"
            binding.detailPrice.text = "Price: ${products.price}"
            binding.detailRating.text ="Rating: ${products.rating}"
            binding.detailStock.text = "Stock: ${products.stock}"
            binding.detailDescription.text = products.description
            Glide.with(requireContext())
                .load(products.thumbnail)
                .into(binding.detailImage)
        }
        binding.btnAddToCart.setOnClickListener {
            val product = productViewModel.selectedProductLiveData.value
            if (product != null) {
                productViewModel.addToCart(product)
                Toast.makeText(
                    requireContext(),
                    "Product added to cart",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}