package com.example.ihramconnect.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ihramconnect.R
import com.example.ihramconnect.view.catalog.CatalogActivity

class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val uploadButton = findViewById<Button>(R.id.uploadButton)
        val recordButton = findViewById<Button>(R.id.recordButton)
        val catalogButton = findViewById<Button>(R.id.catalogButton)

        uploadButton.setOnClickListener {
            // Implementasi untuk upload file TARUH UPLOAD ACTIVITY DISINI UNTUK ADMIN HOME ACTIVITY
        }

        recordButton.setOnClickListener {
            // Implementasi untuk merekam voice note
            startActivity(Intent(this, RecordAudioActivity::class.java))
        }

        catalogButton.setOnClickListener {
            startActivity(Intent(this, CatalogActivity::class.java))
        }
    }
}