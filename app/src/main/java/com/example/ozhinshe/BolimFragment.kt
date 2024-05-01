package com.example.ozhinshe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.adapters.BolimderAdapter
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentBolimBinding

class BolimFragment() : Fragment() {

    private lateinit var binding: FragmentBolimBinding
    private lateinit var adapter: BolimderAdapter
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBolimBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id")
        val seriesCount = arguments?.getInt("seriesCount")
        val posterLink = arguments?.getString("posterLink")

        adapter = BolimderAdapter(posterLink)
        initRecyclerView(adapter, binding.rcView)
        adapter.submitList(seriesCount, id!!)

        binding.imageButton.setOnClickListener { setOnClick(id) }
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
    fun setOnClick(id:Int){
        itemClickListener?.onItemClick(id)
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        val detailedFragment = DetailedFragment()
        detailedFragment.arguments = bundle
        (activity as? HomeActivity)?.replaceFragment(detailedFragment)
    }
}
