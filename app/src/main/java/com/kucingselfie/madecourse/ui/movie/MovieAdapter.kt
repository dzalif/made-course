package com.kucingselfie.madecourse.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kucingselfie.madecourse.databinding.ItemMovieBinding
import com.kucingselfie.madecourse.model.Movie

class MovieAdapter(private val clickListener: (Movie) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Movie, MovieAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            clickListener: (Movie) -> Unit
        ) {
            binding.model = movie
            itemView.setOnClickListener {
                clickListener(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}