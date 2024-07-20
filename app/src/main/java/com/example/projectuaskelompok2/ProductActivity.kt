package com.example.projectuaskelompok2


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuaskelompok2.adapters.ProductAdapter
import com.example.projectuaskelompok2.model.Product
import com.example.projectuaskelompok2.network.ApiClient
import com.example.projectuaskelompok2.network.FavoriteUtils
import com.example.projectuaskelompok2.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: List<Product>
    private lateinit var searchView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        searchView = findViewById(R.id.searchView) // Pastikan ID ini benar sesuai dengan layout XML

        recyclerView = findViewById(R.id.rvProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val apiService = ApiClient.authService
        val call = apiService.getProducts()

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    productList = response.body()!!.data
                    adapter = ProductAdapter(productList, { product ->
                        val intent = Intent(this@ProductActivity, DetailActivity::class.java)
                        intent.putExtra("product", product)
                        startActivity(intent)
                    }, { product ->
                        saveToFavorite(product)
                    })
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@ProductActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@ProductActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().lowercase()
                val filteredList = productList.filter {
                    it.name.lowercase().contains(searchText) || it.desc.lowercase().contains(searchText)
                }
                adapter.updateList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun saveToFavorite(product: Product) {
        FavoriteUtils.saveToFavorite(applicationContext, lifecycleScope, product) { added, removed ->
            runOnUiThread {
                if (added) {
                    Toast.makeText(this@ProductActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged() // Update the icon
                } else if (removed) {
                    Toast.makeText(this@ProductActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged() // Update the icon
                } else {
                    Toast.makeText(this@ProductActivity, "Product is already in favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}