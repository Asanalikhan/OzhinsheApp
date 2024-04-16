package com.example.ozhinshe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.HomeFragment
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.WatchedMovieBinding
import com.example.ozhinshe.modiedata.Movy

class WatchedMovieAdapter(childFragmentManager: FragmentManager): RecyclerView.Adapter<WatchedMovieAdapter.WatchedMovieViewHolder>() {

    private val adapterList = mutableListOf<Movy>()
    private var itemClickListener: OnItemClickListener? = null

    inner class WatchedMovieViewHolder(private val binding: WatchedMovieBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(movy: Movy) {
            binding.rcMovieName.text = movy.name
            binding.rcMovieChapter.text = "${movy.seriesCount} - бөлім"
            val into = Glide.with(binding.root).load(movy.screenshots[0].link)
                .into(binding.rcMovieImg)
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(movy.id)
            }
        }
    }
    fun setOnItemClickListener(listener: HomeFragment) {
        this.itemClickListener = listener
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList1(list: List<Movy>){
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchedMovieViewHolder {
        return WatchedMovieViewHolder(WatchedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: WatchedMovieViewHolder,
        position: Int
    ) {
        holder.onBind(adapterList[itemCount - 1 - position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}