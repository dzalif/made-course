package com.kucingselfie.madecourse.model

import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overview: String
)