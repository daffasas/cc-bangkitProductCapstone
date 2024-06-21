package com.example.ihramconnect.view.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R
import com.example.ihramconnect.data.Recording

class RecordingJamaahAdapter(private val recordings: List<Recording>) : RecyclerView.Adapter<RecordingJamaahAdapter.RecordingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recording_jamaah, parent, false)
        return RecordingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordingViewHolder, position: Int) {
        val recording = recordings[position]
        holder.titleTextView.text = recording.title
        holder.urlTextView.text = recording.url
        holder.filePathTextView.text = recording.filePath
    }

    override fun getItemCount(): Int = recordings.size

    class RecordingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val urlTextView: TextView = itemView.findViewById(R.id.textViewUrl)
        val filePathTextView: TextView = itemView.findViewById(R.id.textViewFilePath)
    }
}