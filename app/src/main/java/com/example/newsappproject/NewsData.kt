package com.example.newsappproject

data class NewsData(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)