package com.example.ozhinshe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.HomeFragment
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.MovieCardBinding
import com.example.ozhinshe.modiedata.Movy


class MainPageAdapter(childFragmentManager: FragmentManager) : RecyclerView.Adapter<MainPageAdapter.MainPageViewHolder>() {
    private val adapterList = mutableListOf<Movy>()
    private var itemClickListener: OnItemClickListener? = null

    inner class MainPageViewHolder(private var binding: MovieCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Movy) {
            binding.rcMovieName.text = item.name
            binding.rcMovieDesc.text = item.description
            val into = Glide.with(binding.root).load(item.screenshots[0].link)
                .into(binding.rcMovieImg)
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(item.id)
            }
        }
    }
    fun setOnItemClickListener(listener: HomeFragment) {
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
    ): MainPageViewHolder {
        return MainPageViewHolder(
            MovieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}
