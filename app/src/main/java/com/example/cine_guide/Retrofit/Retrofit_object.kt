package com.example.cine_guide.Retrofit

import com.example.cine_guide.Api
import com.example.cine_guide.GenreApi
import com.example.cine_guide.SearchApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;

object Retrofit_object {
    private val interceptor: HttpLoggingInterceptor =  HttpLoggingInterceptor().apply {
        level  = HttpLoggingInterceptor.Level.BODY
    }

    private val client:OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val api: Api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.themoviedb.org/")
        .client(client)
        .build()
        .create(Api::class.java)

    val searchapi: SearchApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.themoviedb.org/")
        .client(client)
        .build()
        .create(SearchApi::class.java)

    val genre_api: GenreApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.themoviedb.org/")
        .client(client)
        .build()
        .create(GenreApi::class.java)
}