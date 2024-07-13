package com.example.ozhinshe.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.presentation.basic.HomeFragment
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.databinding.CardMovieBinding

class TelehikayaAdapter(childFragmentManager: FragmentManager):RecyclerView.Adapter<TelehikayaAdapter.TelehikayaViewHolder>() {

    private val adapterList = mutableListOf<com.example.ozhinshe.domain.models.Movy>()
    private var itemClickListener: OnItemClickListener? = null

    inner class TelehikayaViewHolder(private val binding: CardMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(movy: com.example.ozhinshe.domain.models.Movy) {
            binding.rcMovieName.text = movy.name
            binding.rcMovieGenre.text = movy.genres.last().name
            val into = Glide.with(binding.root).load(movy.poster.link)
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
    fun submitList(list: List<com.example.ozhinshe.domain.models.Movy>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TelehikayaViewHolder {
        return TelehikayaViewHolder(CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TelehikayaViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}