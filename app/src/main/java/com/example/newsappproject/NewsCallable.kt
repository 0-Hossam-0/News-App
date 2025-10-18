package com.example.newsappproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {

    @GET("/v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") countryCode: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Call<NewsData>

    @GET("/v2/everything")
    fun getNews(
        @Query("q") query: String = "bitcoin",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Call<NewsData>
}