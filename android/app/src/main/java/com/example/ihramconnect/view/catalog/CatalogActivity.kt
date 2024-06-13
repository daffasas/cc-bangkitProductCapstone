package com.example.ihramconnect.view.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R

class CatalogActivity : AppCompatActivity() {
    private val voiceNotes = listOf(
        VoiceNote(1, "Voice Note 1", "https://example.com/v1.mp3"),
        VoiceNote(2, "Voice Note 2", "https://example.com/v2.mp3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = VoiceNoteAdapter(voiceNotes)
    }
}