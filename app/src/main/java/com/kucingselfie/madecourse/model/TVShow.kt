package com.kucingselfie.madecourse.model

import com.google.gson.annotations.SerializedName

data class TVShow(
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String
)