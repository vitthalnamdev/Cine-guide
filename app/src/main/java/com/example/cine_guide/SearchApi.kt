package com.example.cine_guide

import android.hardware.biometrics.BiometricManager.Strings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.example.cine_guide.models.Products
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi{
    var url:String
    @GET("3/search/movie")
    suspend fun getProductsList(
        @Query("query") query:String?,
        @Query("api_key") api_key:String
    ):Products
}