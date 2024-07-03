package com.example.cine_guide.models

data class Products(
    val page: Int,
    val results: List<Product>,
    val total_pages: Int,
    val total_results: Int
)