package com.example.projectuaskelompok2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.projectuaskelompok2.databinding.ActivityRegisterBinding
import com.example.projectuaskelompok2.model.RegisterRequest
import com.example.projectuaskelompok2.model.RegisterResponse
import com.example.projectuaskelompok2.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val authService by lazy { ApiClient.authService }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            register(username, password)

            val i = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun register(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            // Tampilkan pesan error jika username atau password kosong
            Log.d("Register", "Username and Password are required")
            return
        }

        val registerRequest = RegisterRequest(username, password)
        val call = authService.register(registerRequest)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Log.d("Register", "Status: ${registerResponse?.status}, Message: ${registerResponse?.message}")
                    // Lakukan sesuatu saat registrasi berhasil, misalnya navigasi ke activity lain
                } else {
                    Log.d("Register", "Request failed")
                    // Tampilkan pesan error ke pengguna
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Register", "Request error: ${t.message}")
                // Tampilkan pesan error ke pengguna
            }
        })
    }
}