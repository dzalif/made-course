package com.kucingselfie.madecourse.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kucingselfie.madecourse.databinding.ItemTvshowBinding
import com.kucingselfie.madecourse.model.TVShow

class TVShowAdapter(private val clickListener: (TVShow) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<TVShow, TVShowAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding: ItemTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: TVShow,
            clickListener: (TVShow) -> Unit
        ) {
            binding.model = movie
            itemView.setOnClickListener {
                clickListener(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTvshowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvshow = getItem(position)
        holder.bind(tvshow, clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem == newItem
        }
    }
}