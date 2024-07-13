package com.example.ozhinshe.presentation.basic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.R
import com.example.ozhinshe.databinding.FragmentSearchBinding
import com.example.ozhinshe.presentation.adapters.SearchAdapter
import com.example.ozhinshe.domain.utils.OnItemClickListener
import com.example.ozhinshe.domain.viewmodels.SearchViewModel

class SearchFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewAdapters()
        initRecyclerView(adapter, binding.rcView)
        (activity as HomeActivity).showBottomNavigationView()
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.apply {
            val textViewIds = arrayOf(telehikaya, seatcom, korkem, multfilm, multserial, anime, tvAndShow, derekti, music, shetel)
            for (textView in textViewIds) {
                textView.setOnClickListener {
                    val sanattarFragment = SanattarFragment().apply {
                        arguments = Bundle().apply { putString("string", textView.text.toString()) }
                    }
                    findNavController().navigate(R.id.action_searchFragment_to_sanattarFragment)
                }
            }
        }
        binding.searchBtnIcon.setOnClickListener {
            hideKeyboard(it)
            val query = binding.searchBtnText.editText?.text.toString().trim()
            binding.sanatIzdeu.text = "Іздеу нәтижелері"
            binding.rcView.visibility = View.VISIBLE
            binding.relativeLayout.visibility = View.GONE
            viewModel.getMovie(query)
            viewModel.search.observe(viewLifecycleOwner, Observer { movies ->
                adapter.submitList(movies)
            })
        }
        binding.apply {
            imageButton.setOnClickListener {
                sanatIzdeu.text = "Санаттар"
                rcView.visibility = View.GONE
                relativeLayout.visibility = View.VISIBLE
                searchBtnText.editText?.setText("")
                searchBtnText.clearFocus()
            }
        }
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
    private fun initRecyclerViewAdapters() {
        adapter = SearchAdapter()
        adapter.setOnItemClickListener1(this)
    }
    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_searchFragment_to_detailedFragment, bundle)
    }
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onSeasonClick(id: Int) {
        TODO("Not yet implemented")
    }
}
