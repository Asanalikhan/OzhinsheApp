package com.example.ozhinshe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.GenresCardBinding
import com.example.ozhinshe.modiedata.CategoryAgesItem

class AgesAdapter: RecyclerView.Adapter<AgesAdapter.AgesViewHolder>() {

    private val adapterList = mutableListOf<CategoryAgesItem>()
    inner class AgesViewHolder(private val binding: GenresCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(categoryAgesItem: CategoryAgesItem) {
            binding.rcGenreName.text = categoryAgesItem.name
            val into = Glide.with(binding.root).load(categoryAgesItem.link)
                .into(binding.rcGenreImg)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CategoryAgesItem>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgesViewHolder {
        return AgesViewHolder(GenresCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: AgesViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }
    override fun getItemCount(): Int {
        return adapterList.size
    }
}