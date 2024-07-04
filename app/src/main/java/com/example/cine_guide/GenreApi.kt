package com.example.cine_guide

import com.example.cine_guide.modelgenre.Genres
import com.example.cine_guide.models.Products
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {
    @GET("3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") api_key:String
    ):Genres

}