package com.example.ozhinshe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.MovieCardBinding
import com.example.ozhinshe.modiedata.Movy

class MainPageAdapter():RecyclerView.Adapter<MainPageAdapter.MainPageViewHolder>() {

    private val adapterList = mutableListOf<Movy>()
    inner class MainPageViewHolder(private var binding: MovieCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: Movy){
            binding.rcMovieName.text = item.name
            binding.rcMovieDesc.text = item.description
            val into = Glide.with(binding.root).load(item.poster.link)
                .into(binding.rcMovieImg)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Movy>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageAdapter.MainPageViewHolder {
        return MainPageViewHolder(MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainPageAdapter.MainPageViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}

