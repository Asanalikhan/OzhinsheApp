package com.example.ozhinshe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozhinshe.databinding.BolimCardBinding

class BolimderAdapter(private val imageDrawable: String?) : RecyclerView.Adapter<BolimderAdapter.BolimderViewHolder>() {

    private val adapterList = mutableListOf<Int>()
    private var id: Int = 0

    inner class BolimderViewHolder(private val binding: BolimCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val separator: View = binding.separator
        fun onBind(item: Int, position: Int) {
            binding.rcTextView.text = item.toString()
            val into = Glide.with(binding.root).load(imageDrawable)
                .into(binding.rcImageView)
            if (position == adapterList.size - 1) {
                separator.visibility = View.GONE
            } else {
                separator.visibility = View.VISIBLE
            }
        }
    }

    fun submitList(int: Int?, id: Int) {
        this.id = id
        adapterList.clear()
        int?.let {
            adapterList.addAll(1..it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BolimderViewHolder {
        return BolimderViewHolder(
            BolimCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BolimderViewHolder, position: Int) {
        holder.onBind(adapterList[position], position)
    }

    override fun getItemCount(): Int = adapterList.size
}
