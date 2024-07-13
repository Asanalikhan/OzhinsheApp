package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.GenresCardBinding
import com.example.ozhinshe.domain.models.Screenshot

class ScreenshotAdapter:RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder>() {
    private val adapterList = mutableListOf<com.example.ozhinshe.domain.models.Screenshot>()
    inner class ScreenshotViewHolder(private val binding: GenresCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(screenshot: com.example.ozhinshe.domain.models.Screenshot){
            val into = Glide.with(binding.root).load(screenshot.link)
                .into(binding.rcGenreImg)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<com.example.ozhinshe.domain.models.Screenshot>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScreenshotAdapter.ScreenshotViewHolder {
        return ScreenshotViewHolder(GenresCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ScreenshotAdapter.ScreenshotViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}