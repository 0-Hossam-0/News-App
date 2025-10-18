package com.example.newsappproject

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


class CategoryAdapter(private val categories: List<CategoryData>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.category_title)
        val image: ImageView = itemView.findViewById(R.id.category_image)
        val icon: ImageView = itemView.findViewById(R.id.category_icon)
        val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)

        val card = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.title.text = category.title
        holder.image.setImageResource(category.backgroundImage)
        holder.icon.setImageResource(category.icon)

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(category.startColor, category.endColor)
        )
        gradient.cornerRadius = 40f

        holder.frameLayout.background = gradient
        holder.card.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category", category.title)
            // Handle click event here
            it.findNavController().navigate(R.id.action_homeFragment_to_newsFragment,bundle)
        }
    }

    override fun getItemCount() = categories.size
}

