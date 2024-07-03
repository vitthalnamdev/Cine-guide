package com.example.cine_guide.Repository

import com.example.cine_guide.models.Product
import com.example.cine_guide.movieresult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductList(): Flow<movieresult<List<Product>>>
}