package com.example.newsappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappproject.databinding.FragmentHomeBinding
import androidx.core.graphics.toColorInt


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val categories = listOf(
            CategoryData("Health", R.drawable.health, R.drawable.health_icon,
                "#f78c0c".toColorInt(), "#fec90c".toColorInt()),
            CategoryData("Sports", R.drawable.sport, R.drawable.sports_icon,
                "#5ca793".toColorInt(), "#81dac3".toColorInt()),
            CategoryData("Politics", R.drawable.politics, R.drawable.politics_icon,
                "#f44818".toColorInt(), "#f79d19".toColorInt()),
            CategoryData("World", R.drawable.world, R.drawable.world_icon,
                "#f44818".toColorInt(), "#d5331f".toColorInt()),
            CategoryData("Technology", R.drawable.technology, R.drawable.technology_icon,
                "#a2b86c".toColorInt(), "#bad86e".toColorInt()),
            CategoryData("Fashion", R.drawable.fashion, R.drawable.fashion_icon,
                "#f78c0c".toColorInt(), "#fec90c".toColorInt()),
        )

        val adapter = CategoryAdapter(categories)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

}