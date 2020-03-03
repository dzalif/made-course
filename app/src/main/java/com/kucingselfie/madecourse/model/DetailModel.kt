package com.kucingselfie.madecourse.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String
) : Parcelable

@Parcelize
data class DetailTVShowModel(
    val id: Int,
    val name: String,
    val posterPath: String,
    val overview: String
) : Parcelable

@Parcelize
data class DetailMovieModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
) : Parcelable

