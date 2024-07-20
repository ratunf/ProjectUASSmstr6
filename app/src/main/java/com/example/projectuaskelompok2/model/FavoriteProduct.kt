package com.example.projectuaskelompok2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val id: Int,
    val name: String,
    val desc: String,
    val price: String,
    val image: String,
    val created_at: String,
    val updated_at: String,
    val isFavorite: Boolean = false
)