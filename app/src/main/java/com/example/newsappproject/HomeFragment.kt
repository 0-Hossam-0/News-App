package com.example.newsappproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappproject.databinding.FragmentHomeBinding
import com.example.newsappproject.databinding.FragmentSignUpBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val categories = listOf(
            CategoryData("Health", R.drawable.health, R.drawable.health_icon, Color.parseColor("#f78c0c"),Color.parseColor("#fec90c")),
            CategoryData("Sports", R.drawable.sport, R.drawable.sports_icon, Color.parseColor("#5ca793"),Color.parseColor("#81dac3")),
            CategoryData("Politics", R.drawable.politics, R.drawable.politics_icon,Color.parseColor("#f44818"),Color.parseColor("#f79d19")),
            CategoryData("World", R.drawable.world, R.drawable.world_icon,Color.parseColor("#f44818"),Color.parseColor("#d5331f")),
            CategoryData("Technology", R.drawable.technology, R.drawable.technology_icon,Color.parseColor("#a2b86c"),Color.parseColor("#bad86e")),
            CategoryData("Fashion", R.drawable.fashion, R.drawable.fashion_icon,Color.parseColor("#f78c0c"),Color.parseColor("#fec90c")),
        )

        val adapter = CategoryAdapter(categories)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

}