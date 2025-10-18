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

class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater,container,false)
        val retrofit=Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsCallable=retrofit.create(NewsCallable::class.java)
        val category = arguments?.getString("category") ?: "general" // Fallback to a default

        // 2. Safely get the countries from SharedPreferences.
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val countries = sharedPrefs.getStringSet("selected_countries", emptySet()) ?: emptySet()

        // 3. Combine them into a single, clean query string.
        val queryParts = mutableListOf(category)
        queryParts.addAll(countries)
        val finalQuery = queryParts.joinToString(" OR ") // Use "OR" for better results

        newsCallable.getNews("$queryParts").enqueue(object: Callback<NewsData> {
            override fun onResponse(
                call: Call<NewsData>,
                response: Response<NewsData>
            ) {
                val newsList = response.body()?.articles
                binding.newsList.adapter = NewsAdapter(newsList!!)
                binding.progress.visibility = View.GONE
            }

            override fun onFailure(
                call: Call<NewsData?>,
                t: Throwable
            ) {
                Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
            }

        })
        return binding.root
    }
}