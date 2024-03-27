package com.example.newsdroid

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/"
const val API = "00ac1fb9851f420d8b2e7365de086ed0"

object NewsService {

    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API")
    fun getNews (
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): Call<News>

}

