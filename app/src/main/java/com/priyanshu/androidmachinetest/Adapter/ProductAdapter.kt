package com.priyanshu.androidmachinetest.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyanshu.androidmachinetest.databinding.HomeFragmentBinding
import com.priyanshu.androidmachinetest.databinding.ProductBinding
import com.priyanshu.androidmachinetest.product.Products

class ProductAdapter(
    private val productList: List<Products>,
    private val onProductsClick: (Products) -> Unit
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding: ProductBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = productList[position]

        holder.binding.productName.text = product.title
        holder.binding.productCategory.text = product.category
        holder.binding.productPrice.text = product.price.toString()
        holder.binding.productRating.text = product.rating.toString()
        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.binding.productImg)
        holder.itemView.setOnClickListener {
            onProductsClick(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }




}