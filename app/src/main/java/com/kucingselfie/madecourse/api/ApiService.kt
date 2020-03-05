package com.kucingselfie.madecourse.api

import com.kucingselfie.madecourse.api.response.MovieResponse
import com.kucingselfie.madecourse.api.response.SearchResponse
import com.kucingselfie.madecourse.api.response.TVShowResponse
import com.kucingselfie.madecourse.model.DetailModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") type: String
    ): MovieResponse

    @GET("discover/tv")
    suspend fun getTvShows(
        @Query("api_key") type: String
    ): TVShowResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") type: String
    ) : DetailModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") type: String
    ) : DetailModel

    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : SearchResponse
}