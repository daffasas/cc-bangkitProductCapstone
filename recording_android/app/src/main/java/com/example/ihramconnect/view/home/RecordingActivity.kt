package com.example.ihramconnect.view.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.data.ApiService
import com.example.ihramconnect.data.Recording
import com.example.ihramconnect.view.catalog.RecordingJamaahAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordingActivity : AppCompatActivity() {


    private lateinit var apiService: ApiService


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecordingJamaahAdapter
    private val recordings = mutableListOf<Recording>()


    private fun fetchRecordings() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        var token =  sharedPreferences.getString("auth_token", null);
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getRecordings("Bearer ${token!!}")
                withContext(Dispatchers.Main) {
                    recordings.clear()
                    recordings.addAll(response)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RecordingActivity, "Failed to fetch recordings", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recording)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiService = ApiConfig.apiService;

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRecordings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecordingJamaahAdapter(recordings)
        recyclerView.adapter = adapter

        // Fetch recordings from API
        fetchRecordings()

    }
}