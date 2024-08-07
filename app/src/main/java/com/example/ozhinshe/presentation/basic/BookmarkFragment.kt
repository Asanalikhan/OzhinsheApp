package com.example.ozhinshe.presentation.basic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.R
import com.example.ozhinshe.domain.viewmodels.ItemViewModel
import com.example.ozhinshe.domain.data.MainApi
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentBookmarkBinding
import com.example.ozhinshe.domain.viewmodels.HomeViewModel
import com.example.ozhinshe.presentation.adapters.BookmarkAdapter
import com.example.ozhinshe.presentation.decoration.CardDecoration

class BookmarkFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var mainApi: MainApi
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var adapter: BookmarkAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewAdapters()
        initRecyclerView(adapter, binding.rcView)
        setDivider()
        (activity as HomeActivity).showBottomNavigationView()

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        itemViewModel.getAllData.observe(viewLifecycleOwner, Observer { items ->
            viewModel.getAllData(items)
            viewModel.moviesbookmark.observe(viewLifecycleOwner, Observer { list ->
                adapter.submitList(list)
            })
        })
    }

    private fun setDivider(){
        layoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rcView.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
            rcView.addItemDecoration(CardDecoration(topOffset = 20f.toInt(), bottomOffset = 20f.toInt()))
        }

    }
    private fun getToken(): String{
        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)
        return token.toString()
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
    private fun initRecyclerViewAdapters() {
        adapter = BookmarkAdapter()
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_bookmarkFragment_to_detailedFragment, bundle)
    }

    override fun onSeasonClick(id: Int) {
        TODO("Not yet implemented")
    }
}