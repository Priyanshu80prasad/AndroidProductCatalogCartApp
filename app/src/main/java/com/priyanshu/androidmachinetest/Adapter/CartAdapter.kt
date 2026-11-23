package com.priyanshu.androidmachinetest.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyanshu.androidmachinetest.databinding.CartItemBinding
import com.priyanshu.androidmachinetest.product.Products

class CartAdapter(
    private val cartList : List<Products>,
    private val onRemoveClick: (Products) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(val binding: CartItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartList[position]

        holder.binding.cartName.text = product.title
        holder.binding.cartPrice.text = "Price: ${product.price}"
        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.binding.cartImage)

        holder.binding.btnRemove.setOnClickListener {
            onRemoveClick(product)
        }
    }
    override fun getItemCount(): Int {
        return cartList.size
    }
}