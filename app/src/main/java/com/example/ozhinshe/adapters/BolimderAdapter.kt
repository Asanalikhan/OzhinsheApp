package com.example.ozhinshe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ozhinshe.databinding.BolimCardBinding
import com.example.ozhinshe.modiedata.SeasonResponse
import com.example.ozhinshe.modiedata.Video

class BolimderAdapter (): RecyclerView.Adapter<BolimderAdapter.BolimderViewHolder>() {
    private val adapterList = mutableListOf<Video>()
    inner class BolimderViewHolder(private val binding: BolimCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: Video){
            binding.rcTextView.text = item.number.toString()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: SeasonResponse) {
        adapterList.clear()
        //adapterList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BolimderAdapter.BolimderViewHolder {
        return BolimderViewHolder(
            BolimCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }
    override fun onBindViewHolder(holder: BolimderAdapter.BolimderViewHolder, position: Int) {
        holder.onBind(adapterList[position])
    }
    override fun getItemCount(): Int {
        return adapterList.size
    }
}
