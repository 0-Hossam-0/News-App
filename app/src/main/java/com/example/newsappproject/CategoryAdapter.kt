package com.example.newsappproject

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CategoryAdapter(private val categories: List<CategoryData>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.category_title)
        val image: ImageView = itemView.findViewById(R.id.category_image)
        val icon: ImageView = itemView.findViewById(R.id.category_icon)
        val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)
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
    }

    override fun getItemCount() = categories.size
}

