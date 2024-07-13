package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.presentation.basic.BookmarkFragment
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.databinding.SearchCardBinding

class BookmarkAdapter(): RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    private val adapterList = mutableListOf<com.example.ozhinshe.domain.models.Movy>()
    private var itemClickListener: OnItemClickListener? = null
    inner class BookmarkViewHolder(private val binding: SearchCardBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item: com.example.ozhinshe.domain.models.Movy){
            val into = Glide.with(binding.root).load(item.poster.link)
                .into(binding.srcMovieImage)
            binding.srMovieName.text = item.name
            binding.srMovieDesc.text = "" + item.year + "." + item.movieType + "."  + item.categories[0].name
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(item.id)
            }
        }
    }
    fun setOnItemClickListener(listener: BookmarkFragment) {
        this.itemClickListener = listener
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<com.example.ozhinshe.domain.models.Movy>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }
}