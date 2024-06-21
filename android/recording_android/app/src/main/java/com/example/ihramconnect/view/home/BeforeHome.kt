package com.example.ihramconnect.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ihramconnect.R

class BeforeHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_before_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val cardViewKatalogWisata: View = findViewById(R.id.wisataCard)
        val cardViewRekamanAudio: View = findViewById(R.id.recordingCard)

        // Set onClickListeners
        cardViewKatalogWisata.setOnClickListener {
            val intent = Intent(this, JamaahActivity::class.java)
            startActivity(intent)
        }

        cardViewRekamanAudio.setOnClickListener {
            val intent = Intent(this, RecordingActivity::class.java)
            startActivity(intent)
        }

    }


}