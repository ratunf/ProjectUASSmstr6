package com.example.projectuaskelompok2.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectuaskelompok2.model.FavoriteProduct

@Dao
interface FavoriteProductDao {
    @Query("SELECT * FROM favorite_products")
    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>>

    @Query("SELECT * FROM favorite_products")
    suspend fun getAllFavorites(): List<FavoriteProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteProduct: FavoriteProduct): Long

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProduct)

    @Query("DELETE FROM favorite_products WHERE id = :productId")
    suspend fun deleteById(productId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE id = :productId)")
    suspend fun isFavorite(productId: Int): Boolean
}
