package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.presentation.basic.DetailedFragment
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.databinding.CardMovieBinding

class UqsasAdapter(childFragmentManager: FragmentManager): RecyclerView.Adapter<UqsasAdapter.UqsasViewHolder>() {
    private val adapterList = mutableListOf<com.example.ozhinshe.domain.models.ContentX>()
    private var itemClickListener: OnItemClickListener? = null
    inner class UqsasViewHolder(private val binding: CardMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(movy : com.example.ozhinshe.domain.models.ContentX){
            binding.rcMovieName.text = movy.name
            binding.rcMovieGenre.text = movy.genres.last().name
            val into = Glide.with(binding.root).load(movy.poster.link)
                .into(binding.rcMovieImg)
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(movy.id)
            }
        }
    }
    fun setOnItemClickListener(listener: DetailedFragment) {
        this.itemClickListener = listener
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<com.example.ozhinshe.domain.models.ContentX>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UqsasViewHolder {
        return UqsasViewHolder(CardMovieBinding.inflate(LayoutInflater.from(parent.context),  parent, false))
    }

    override fun onBindViewHolder(holder: UqsasViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}