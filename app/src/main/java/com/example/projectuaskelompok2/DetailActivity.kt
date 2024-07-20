package com.example.projectuaskelompok2

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.projectuaskelompok2.model.Product
import com.example.projectuaskelompok2.network.AppDatabase
import com.example.projectuaskelompok2.network.FavoriteUtils
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewDesc: TextView
    private lateinit var buttonFavorite: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.imageView)
        textViewName = findViewById(R.id.textViewName)
        textViewDesc = findViewById(R.id.textViewDesc)
        buttonFavorite = findViewById(R.id.buttonFavorite)

        val product = intent.getParcelableExtra<Product>("product")

        product?.let { product ->
            textViewName.text = product.name
            textViewDesc.text = product.desc
            Glide.with(this).load(product.image).into(imageView)

            lifecycleScope.launch {
                val isFavorite = AppDatabase.getDatabase(applicationContext).favoriteProductDao().isFavorite(product.id)
                buttonFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite)
            }

            buttonFavorite.setOnClickListener {
                FavoriteUtils.saveToFavorite(applicationContext, lifecycleScope, product) { added, removed ->
                    runOnUiThread {
                        buttonFavorite.setImageResource(if (added) R.drawable.ic_favorite else R.drawable.ic_favorite)
                        if (added) {
                            Toast.makeText(this@DetailActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
                        } else if (removed) {
                            Toast.makeText(this@DetailActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@DetailActivity, "Product is already in favorites", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}