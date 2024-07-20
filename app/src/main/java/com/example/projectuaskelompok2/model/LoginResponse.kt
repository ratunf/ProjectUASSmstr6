package com.example.projectuaskelompok2.model

class LoginResponse(
    val status: Boolean,
    val message: String,
    val data: Data?
)  {
    data class Data(
        val id: Int,
        val username: String
    )
}