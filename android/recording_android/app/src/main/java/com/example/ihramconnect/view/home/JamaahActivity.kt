package com.example.ihramconnect.view.home

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.data.ApiService
import com.example.ihramconnect.data.Place
import com.example.ihramconnect.data.PlaceResponse
import com.example.ihramconnect.view.catalog.PlaceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JamaahActivity : AppCompatActivity() {

    private lateinit var placeRv: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    private var places: MutableList<Place> = mutableListOf()

    private lateinit var apiService: ApiService

    private var currentPage = 1
    private val pageSize = 12

    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jamaah)

        apiService = ApiConfig.apiService

        placeRv = findViewById(R.id.place_rv)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)

        placeRv.layoutManager = LinearLayoutManager(this)

        placeAdapter = PlaceAdapter(places)
        placeRv.adapter = placeAdapter

        // Setup onClickListener untuk button Next
        btnNext.setOnClickListener {
            fetchPlaces(currentPage + 1)
        }

        // Setup onClickListener untuk button Previous
        btnPrevious.setOnClickListener {
            if (currentPage > 1) {
                fetchPlaces(currentPage - 1)
            }
        }

        // Ambil data dari API untuk halaman pertama
        fetchPlaces(currentPage)
    }

    private fun fetchPlaces(page: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        var token =  sharedPreferences.getString("auth_token", null);

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPlaces("Bearer ${token}", page, pageSize)
                withContext(Dispatchers.Main) {
                    if (page == 1) {
                        places.clear()
                    }
                    places.addAll(response.data)
                    placeAdapter.notifyDataSetChanged()
                    currentPage = page // Update currentPage sesuai dengan halaman yang diambil
                    updateButtonVisibility() // Perbarui visibility tombol Previous dan Next
                }
            } catch (e: Exception) {
                Log.e("JamaahActivity", "Error fetching places: ${e.message}")
                // Handle error
            }
        }
    }

    private fun updateButtonVisibility() {
        btnPrevious.visibility = if (currentPage > 1) {
            Button.VISIBLE
        } else {
            Button.INVISIBLE
        }

        btnNext.visibility = if (places.size < pageSize) {
            Button.INVISIBLE
        } else {
            Button.VISIBLE
        }
    }
}
