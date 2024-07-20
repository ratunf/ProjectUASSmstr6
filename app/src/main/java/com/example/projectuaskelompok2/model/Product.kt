package com.example.projectuaskelompok2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Product (
    val id: Int,
    val name: String,
    val desc: String,
    val price: String,
    val image: String,
    val created_at: String,
    val updated_at: String,
    var isFavorite: Boolean = false
) : Parcelable