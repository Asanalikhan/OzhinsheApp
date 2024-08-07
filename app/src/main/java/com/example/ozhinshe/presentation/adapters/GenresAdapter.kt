package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.GenresCardBinding
import com.example.ozhinshe.domain.models.Content

class GenresAdapter: RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private val adapterList = mutableListOf<com.example.ozhinshe.domain.models.Content>()

    inner class GenresViewHolder(private val binding: GenresCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(content: com.example.ozhinshe.domain.models.Content) {
            binding.rcGenreName.text = content.name
            val into = Glide.with(binding.root).load(content.link)
                .into(binding.rcGenreImg)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<com.example.ozhinshe.domain.models.Content>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresViewHolder {
        return GenresViewHolder(GenresCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}