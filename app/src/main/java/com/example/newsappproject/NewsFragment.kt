package com.example.newsappproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newsappproject.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.newsappproject.BuildConfig

class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsCallable = retrofit.create(NewsCallable::class.java)
        val category = arguments?.getString("category") ?: "general"

        binding.progress.visibility = View.VISIBLE
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val countries = sharedPrefs.getStringSet("selected_countries", emptySet()) ?: emptySet()


        val queryParts = mutableListOf(category)
        queryParts.addAll(countries)
        val finalQuery = queryParts.joinToString(" OR ")

        newsCallable.getNews(finalQuery).enqueue(object : Callback<NewsData> {
            override fun onResponse(
                call: Call<NewsData>,
                response: Response<NewsData>
            ) {
                if (response.isSuccessful) {
                    val newsList = response.body()?.articles
                    if (newsList != null && newsList.isNotEmpty()) {
                        Log.d("NewsFragment", "Received articles: ${newsList.size}")
                        binding.newsList.adapter = NewsAdapter(newsList)
                    } else {
                        Log.e("NewsFragment", "Received empty or null articles list")
                        binding.newsList.adapter = NewsAdapter(emptyList())
                        Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("NewsFragment", "Response not successful: ${response.code()}")
                    binding.newsList.adapter = NewsAdapter(emptyList())
                    Toast.makeText(requireContext(), "Failed to retrieve news", Toast.LENGTH_SHORT).show()
                }
                binding.progress.visibility = View.GONE
            }

            override fun onFailure(
                call: Call<NewsData>,
                t: Throwable
            ) {
                Log.e("NewsFragment", "Failed to load news", t)
                binding.newsList.adapter = NewsAdapter(emptyList())
                Toast.makeText(requireContext(), "Failed to load news", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.GONE
            }
        })
        return binding.root
    }
}