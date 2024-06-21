package com.example.ihramconnect.view.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.data.Recording
import com.example.ihramconnect.R

class RecordingsAdapter(
    private val recordings: List<Recording>,
    private val onDeleteClick: (Recording) -> Unit
) : RecyclerView.Adapter<RecordingsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recordingName: TextView = view.findViewById(R.id.recordingName)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recording, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recording = recordings[position]
        holder.recordingName.text = recording.title
        holder.deleteButton.setOnClickListener { onDeleteClick(recording) }
    }

    override fun getItemCount(): Int = recordings.size
}