package com.example.newsappproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappproject.databinding.FragmentFavourites2Binding
class NewsAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = FragmentFavourites2Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val news = newsList[position]

        holder.articleText?.text = news.title ?: "No title available"
        Glide.with(holder.itemView.context)
            .load(news.urlToImage)
            .into(holder.articleImage!!)
        holder.dateText?.text = news.publishedAt?: "Unknown date"
        holder.userName?.text = news.author?: "Unknown author"
        holder.itemView.setOnClickListener {
            val rawUrl = news.url
            if (rawUrl.isNullOrBlank()) return@setOnClickListener

            val url = if (!rawUrl.startsWith("http")) "https://$rawUrl" else rawUrl
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                holder.itemView.context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }
        }



    override fun getItemCount(): Int = newsList.size

    class NewsViewHolder(binding: FragmentFavourites2Binding) : RecyclerView.ViewHolder(binding.root) {
        val articleImage = binding.articleImage
        val articleText = binding.articleText
        val dateText = binding.dateText
        val userName = binding.userName
    }


}