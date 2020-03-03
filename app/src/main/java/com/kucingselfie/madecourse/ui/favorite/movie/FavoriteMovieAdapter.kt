package com.kucingselfie.madecourse.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucingselfie.madecourse.databinding.ItemFavoriteMovieBinding
import com.kucingselfie.madecourse.entity.MovieEntity

class FavoriteMovieAdapter(private val clickListener: (MovieEntity) -> Unit) :
    ListAdapter<MovieEntity, FavoriteMovieAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: MovieEntity,
            clickListener: (MovieEntity) -> Unit
        ) {
            binding.model = movie
            itemView.setOnClickListener {
                clickListener(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}