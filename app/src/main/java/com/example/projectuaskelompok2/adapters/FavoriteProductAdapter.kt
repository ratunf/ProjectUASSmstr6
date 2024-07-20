package com.example.projectuaskelompok2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectuaskelompok2.R
import com.example.projectuaskelompok2.model.FavoriteProduct

class FavoriteProductAdapter (
    private var favoriteProducts: List<FavoriteProduct>,
    private val showRemoveButton: Boolean,
    private val removeFromFavorite: (FavoriteProduct) -> Unit
) : RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = favoriteProducts[position]
        holder.name.text = product.name
        holder.desc.text = product.desc
//        holder.price.text = product.price


        Glide.with(holder.itemView.context).load(product.image).into(holder.image)
        if (showRemoveButton) {
            holder.removeButton.visibility = View.VISIBLE
            holder.removeButton.setOnClickListener { removeFromFavorite(product) }
        } else {
            holder.removeButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = favoriteProducts.size

    fun updateList(newList: List<FavoriteProduct>) {
        favoriteProducts = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.product_name)
        val desc: TextView = itemView.findViewById(R.id.product_desc)
        //        val price: TextView = itemView.findViewById(R.id.product_price)
        val image: ImageView = itemView.findViewById(R.id.product_image)
        val removeButton: Button = itemView.findViewById(R.id.btn_remove_favorite)
    }
}
