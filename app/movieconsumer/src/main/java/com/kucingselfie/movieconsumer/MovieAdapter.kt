package com.kucingselfie.movieconsumer

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kucingselfie.movieconsumer.common.Constant.BASE_URL_IMAGE
import com.kucingselfie.movieconsumer.common.Constant.COLUMN_DESCRIPTION
import com.kucingselfie.movieconsumer.common.Constant.COLUMN_POSTER
import com.kucingselfie.movieconsumer.common.Constant.COLUMN_TITLE
import com.kucingselfie.movieconsumer.databinding.ItemMovieBinding
import timber.log.Timber

class MovieAdapter(var cursor: Cursor?, val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        var size = 0
        cursor?.let {
            size = it.count
        }
        return size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor?.let {
            holder.bind(it.moveToPosition(position), it, context)
        }
    }

    fun submit(c: Cursor?) {
        cursor = c
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.textView
        private val description = binding.textView2
        private val image = binding.imageView

        fun bind(
            moveToPosition: Boolean,
            cursor: Cursor,
            context: Context
        ) {
            if (moveToPosition) {
                title.text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                description.text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER))
                Glide.with(context).load(BASE_URL_IMAGE + imageUri).into(image)
            }
        }
    }
}