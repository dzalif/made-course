package com.kucingselfie.madecourse.ui.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucingselfie.madecourse.databinding.ItemFavoriteMovieBinding
import com.kucingselfie.madecourse.databinding.ItemFavoriteTvshowBinding
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.entity.TVShowEntity

class FavoriteTVShowAdapter(private val clickListener: (TVShowEntity) -> Unit) :
    ListAdapter<TVShowEntity, FavoriteTVShowAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding: ItemFavoriteTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            tvShow: TVShowEntity,
            clickListener: (TVShowEntity) -> Unit
        ) {
            binding.model = tvShow
            itemView.setOnClickListener {
                clickListener(tvShow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoriteTvshowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TVShowEntity>() {
        override fun areItemsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
            return oldItem == newItem
        }
    }
}