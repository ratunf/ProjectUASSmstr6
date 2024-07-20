package com.example.projectuaskelompok2.network

import android.content.Context
import com.example.projectuaskelompok2.model.FavoriteProduct
import com.example.projectuaskelompok2.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object FavoriteUtils {
    fun saveToFavorite(context: Context, scope: CoroutineScope, product: Product, onResult: (Boolean, Boolean) -> Unit) {
        scope.launch {
            val favoriteProductDao = AppDatabase.getDatabase(context).favoriteProductDao()
            val isFavorite = favoriteProductDao.isFavorite(product.id)
            if (isFavorite) {
                favoriteProductDao.deleteById(product.id)
                onResult(false, true)
            } else {
                val favoriteProduct = FavoriteProduct(
                    id = product.id,
                    name = product.name,
                    desc = product.desc,
                    price = product.price,
                    image = product.image,
                    created_at = product.created_at,
                    updated_at = product.updated_at
                )
                favoriteProductDao.insert(favoriteProduct)
                onResult(true, false)
            }
        }
    }
}