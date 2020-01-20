package com.kucingselfie.madecourse

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kucingselfie.madecourse.model.Movie
import com.kucingselfie.madecourse.ui.movie.MovieAdapter

@BindingAdapter("listMovie")
fun listMovie(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

@BindingAdapter("bindImage")
fun bindImage(imgView: ImageView, imgUrl: Int) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .into(imgView)
}