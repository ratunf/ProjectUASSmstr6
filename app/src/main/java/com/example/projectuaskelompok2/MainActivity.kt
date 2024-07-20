package com.example.projectuaskelompok2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projectuaskelompok2.databinding.ActivityMainBinding
import com.example.projectuaskelompok2.model.LoginRequest
import com.example.projectuaskelompok2.network.ApiClient
import com.example.projectuaskelompok2.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val authService by lazy { ApiClient.authService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Periksa SharedPreferences
        val sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Jika pengguna sudah login, langsung navigasikan ke HomeActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)


            binding.btnLogin.setOnClickListener {
                val username = binding.edtUsername.text.toString()
                val password = binding.edtPassword.text.toString()
                login(username, password)
            }

            binding.tvSignup.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username = username, password = password)
        val call = authService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("Login", "Status: ${loginResponse?.status}, Message: ${loginResponse?.message}")

                    val sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putBoolean("isLoggedIn", true)
                        putString("username", username)
                        apply()
                    }
                    // Lakukan sesuatu saat login berhasil, misalnya navigasi ke activity lain
                    val i = Intent(this@MainActivity, MenuActivity::class.java)
                    startActivity(i)
                } else {
                    Log.d("Login", "Request failed")
                    // Tampilkan pesan error ke pengguna
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Login", "Request error: ${t.message}")
                // Tampilkan pesan error ke pengguna
            }
        })
    }
}