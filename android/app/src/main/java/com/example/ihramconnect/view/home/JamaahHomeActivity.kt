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

class JamaahHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jamaah_home)

        val catalogButton = findViewById<Button>(R.id.catalogButton)

        catalogButton.setOnClickListener {
            startActivity(Intent(this, CatalogActivity::class.java))
        }
    }
}