package com.example.newsdroid

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)