package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.presentation.basic.SanattarFragment
import com.example.ozhinshe.presentation.basic.SearchFragment
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.databinding.SearchCardBinding
import com.example.ozhinshe.domain.models.SearchResponce
import com.example.ozhinshe.domain.models.SearchResponse

class SearchAdapter(): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val adapterList = mutableListOf<SearchResponse>()
    private var itemClickListener: OnItemClickListener? = null
    inner class SearchViewHolder(private val binding: SearchCardBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item: SearchResponse) {
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
    fun setOnItemClickListener1(listener: SearchFragment) {
        this.itemClickListener = listener
    }
    fun submitList(list: SearchResponce) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        return SearchViewHolder(SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

}

