package com.priyanshu.androidmachinetest.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshu.androidmachinetest.Adapter.CartAdapter
import com.priyanshu.androidmachinetest.databinding.CartFragmentBinding
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModel
import com.priyanshu.androidmachinetest.viewmodel.ProductViewModelFactory

class CartFragment : Fragment() {
    private lateinit var binding: CartFragmentBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProvider(
            requireActivity(),
            ProductViewModelFactory()
        )[ProductViewModel::class.java]
        binding.cartRecycle.layoutManager = LinearLayoutManager(requireContext())

        productViewModel.cartData.observe(viewLifecycleOwner) { cart ->
            binding.cartRecycle.adapter = CartAdapter(cart) {products ->
                productViewModel.removeFromCart(products)
            }
            binding.totalItems.text = "Total Item: ${cart.size}"
            val total = cart.sumOf { it.price }
            binding.totalAmount.text = "Total Amount: $total"

        }
        binding.btnClearCart.setOnClickListener {
            productViewModel.clearCart()
        }

        binding.btnCheckout.setOnClickListener {
            val cart = productViewModel.cartData.value

            if (cart.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Cart Is empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),"Order Placed Successfully", Toast.LENGTH_SHORT).show()
            productViewModel.clearCart()
            }
        }
    }

}