package com.example.projectuaskelompok2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectuaskelompok2.databinding.ActivityBiodataBinding

class BiodataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiodataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}