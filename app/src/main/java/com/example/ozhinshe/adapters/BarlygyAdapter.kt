package com.example.ozhinshe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.SanattarFragment
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.SearchCardBinding
import com.example.ozhinshe.modiedata.ContentX

class BarlygyAdapter(): RecyclerView.Adapter<BarlygyAdapter.BarlygyViewHolder>() {

    private val adapterList = mutableListOf<ContentX>()
    private var itemClickListener: OnItemClickListener? = null
    inner class BarlygyViewHolder(private val binding: SearchCardBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item: ContentX, position: Int) {
            val into = Glide.with(binding.root).load(item.poster.link)
                .into(binding.srcMovieImage)
            binding.srMovieName.text = item.name
            binding.srMovieDesc.text = "" + item.year + "." + item.movieType + "."  + item.categories[0].name
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(item.id)
            }
        }
    }
    fun setOnItemClickListener(listener: SanattarFragment) {
        this.itemClickListener = listener
    }
    fun submitList(list: List<ContentX>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BarlygyViewHolder {
        return BarlygyViewHolder(SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BarlygyAdapter.BarlygyViewHolder, position: Int) {
        holder.onBind(adapterList[position], position)
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

}

