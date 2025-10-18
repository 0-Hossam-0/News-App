package com.example.newsappproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappproject.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    private lateinit var _binding: FragmentFavouritesBinding
    private val binding get() = _binding
    private val articles = mutableListOf<Article>()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(articles)
        binding.newsList.layoutManager = LinearLayoutManager(requireContext())
        binding.newsList.adapter = adapter
    }
    private fun setupListeners() {
        binding.newsList.setOnClickListener {
        }
    }
}
