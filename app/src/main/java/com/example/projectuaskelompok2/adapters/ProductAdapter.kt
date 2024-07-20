package com.example.projectuaskelompok2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectuaskelompok2.R
import com.example.projectuaskelompok2.model.Product
import com.example.projectuaskelompok2.network.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter (
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDesc: TextView = itemView.findViewById(R.id.product_desc)
        //        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)

        fun bind(product: Product) {
            productName.text = product.name
            productDesc.text = product.desc
//            productPrice.text = product.price
            Glide.with(itemView.context).load(product.image).into(productImage)

            itemView.setOnClickListener {
                onProductClick(product)
            }

            // Check if product is in favorites and update the icon
            GlobalScope.launch {
                val isFavorite = AppDatabase.getDatabase(itemView.context).favoriteProductDao().isFavorite(product.id)
                withContext(Dispatchers.Main) {
                    favoriteButton.setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_border)
                }
            }

            favoriteButton.setOnClickListener {
                onFavoriteClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}