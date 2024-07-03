package com.example.cine_guide

import android.hardware.biometrics.BiometricManager.Strings
import com.example.cine_guide.models.Products
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{
    var url:String
    @GET("3/movie/popular")
    suspend fun getProductsList(
        @Query("api_key") api_key:String
    ):Products
}