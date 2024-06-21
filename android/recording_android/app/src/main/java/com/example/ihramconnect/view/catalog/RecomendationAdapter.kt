package com.example.ihramconnect.view.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ihramconnect.R
import com.example.ihramconnect.data.Recomendation

class RecomendationAdapter(
    private val recomendations: List<Recomendation>,
    private val onItemClick: (Recomendation) -> Unit
) : RecyclerView.Adapter<RecomendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recomendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recomendation = recomendations[position]
        holder.bind(recomendation, onItemClick)
    }

    override fun getItemCount(): Int {
        return recomendations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(recomendation: Recomendation, onItemClick: (Recomendation) -> Unit) {
            titleTextView.text = recomendation.title
            descriptionTextView.text = recomendation.description
            Glide.with(itemView.context).load(recomendation.url).into(imageView)

            itemView.setOnClickListener {
                onItemClick(recomendation)
            }
        }
    }
}
