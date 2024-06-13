package com.example.ihramconnect.view.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R

class VoiceNoteAdapter(private val voiceNotes: List<VoiceNote>) :
    RecyclerView.Adapter<VoiceNoteAdapter.VoiceNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceNoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_voice_note, parent, false)
        return VoiceNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoiceNoteViewHolder, position: Int) {
        val voiceNote = voiceNotes[position]
        holder.bind(voiceNote)
    }

    override fun getItemCount() = voiceNotes.size

    class VoiceNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val playButton: Button = itemView.findViewById(R.id.playButton)

        fun bind(voiceNote: VoiceNote) {
            titleTextView.text = voiceNote.title
            playButton.setOnClickListener {
                // Implementasi untuk memutar voice note
            }
        }
    }
}