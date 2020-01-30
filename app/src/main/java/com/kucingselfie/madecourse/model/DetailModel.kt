package com.kucingselfie.madecourse.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val overview: String
) : Parcelable