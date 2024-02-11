package com.example.ozhinshe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private val title: List<String>, private val image: List<Int>): RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

   inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val itemTitle: TextView = itemView.findViewById(R.id.tvTitleOnboard)
       val itemImage: ImageView = itemView.findViewById(R.id.imageView)
   }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.onboard_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }
}