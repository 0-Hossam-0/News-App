package com.example.newsappproject

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappproject.databinding.FragmentFavourites2Binding
import com.example.newsappproject.databinding.FragmentNewsBinding

class NewsAdapter(private val newsList: List<NewsData>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = newsList.size

    class NewsViewHolder(binding: FragmentFavourites2Binding) : RecyclerView.ViewHolder(binding.root) {
        val articleImage = binding.articleImage
        val articleText = binding.articleText
        val dateText = binding.dateText
        val userName = binding.userName
        val userIcon = binding.userIcon
    }


}