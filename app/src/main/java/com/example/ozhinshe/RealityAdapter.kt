package com.example.ozhinshe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.CardMovieBinding
import com.example.ozhinshe.modiedata.Movy

class RealityAdapter: RecyclerView.Adapter<RealityAdapter.RealityViewHolder>() {

    private val adapterList = mutableListOf<Movy>()
    inner class RealityViewHolder(private val binding: CardMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(movy: Movy) {
            binding.rcMovieName.text = movy.name
            binding.rcMovieGenre.text = movy.genres.last().name
            val into = Glide.with(binding.root).load(movy.poster.link)
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
    ): RealityAdapter.RealityViewHolder {
        return RealityViewHolder(CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RealityAdapter.RealityViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}