package com.example.projectuaskelompok2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuaskelompok2.adapters.FavoriteProductAdapter
import com.example.projectuaskelompok2.model.FavoriteProduct
import com.example.projectuaskelompok2.network.AppDatabase
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteProductAdapter
    private lateinit var favoriteProducts: MutableList<FavoriteProduct>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.rvFavoriteProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            favoriteProducts = AppDatabase.getDatabase(applicationContext).favoriteProductDao().getAllFavorites().toMutableList()
            adapter = FavoriteProductAdapter(favoriteProducts, showRemoveButton = true) { product -> removeFromFavorite(product) }
            recyclerView.adapter = adapter
        }
    }

    private fun removeFromFavorite(product: FavoriteProduct) {
        lifecycleScope.launch {
            AppDatabase.getDatabase(applicationContext).favoriteProductDao().delete(product)
            favoriteProducts.remove(product)
            adapter.updateList(favoriteProducts)
            Toast.makeText(this@FavoriteActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
        }
    }
}