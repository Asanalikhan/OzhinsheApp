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
import com.example.ozhinshe.modiedata.Movy

class Telehikaya2Adapter(): RecyclerView.Adapter<Telehikaya2Adapter.Telehikaya2ViewHolder>() {

    private val adapterList = mutableListOf<Movy>()
    private var itemClickListener: OnItemClickListener? = null
    inner class Telehikaya2ViewHolder(private val binding: SearchCardBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item: Movy, position: Int) {
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
    fun submitList(list: List<Movy>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Telehikaya2ViewHolder {
        return Telehikaya2ViewHolder(SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: Telehikaya2Adapter.Telehikaya2ViewHolder, position: Int) {
        holder.onBind(adapterList[position], position)
    }
    override fun getItemCount(): Int {
        return adapterList.size
    }
}

