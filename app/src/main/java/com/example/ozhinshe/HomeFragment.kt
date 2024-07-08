package com.example.ozhinshe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ozhinshe.adapters.*
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentHomeBinding
import com.example.ozhinshe.decoration.CardDecoration

class HomeFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: MainPageAdapter
    private lateinit var adapter1: WatchedMovieAdapter
    private lateinit var adapter2: TrendAdapter
    private lateinit var adapter3: ForYouAdapter
    private lateinit var adapter4: GenresAdapter
    private lateinit var adapter5: ZhobalarAdapter
    private lateinit var adapter6: RealityAdapter
    private lateinit var adapter7: TelehikayaAdapter
    private lateinit var adapter8: AgesAdapter
    private lateinit var adapter9: DerektiAdapter
    private lateinit var adapter10: ShetelAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewAdapters()
        initRecyclerViews()
        setOffSetToRecView()
        (activity as HomeActivity).showBottomNavigationView()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            adapter.submitList(movies)
            adapter3.submitList(movies)
        })
        viewModel.wathed.observe(viewLifecycleOwner, Observer { movies ->
            adapter1.submitList1(movies)
            adapter2.submitList(movies)
        })
        viewModel.genres.observe(viewLifecycleOwner, Observer { movies ->
            adapter4.submitList(movies)
        })
        viewModel.zhoba.observe(viewLifecycleOwner, Observer { movies ->
            adapter5.submitList(movies)
        })
        viewModel.reality.observe(viewLifecycleOwner, Observer { movies ->
            adapter6.submitList(movies)
        })
        viewModel.telehikaya.observe(viewLifecycleOwner, Observer { movies ->
            adapter7.submitList(movies)
        })
        viewModel.ages.observe(viewLifecycleOwner, Observer { movies ->
            adapter8.submitList(movies)
        })
        viewModel.derekti.observe(viewLifecycleOwner, Observer { movies ->
            adapter9.submitList(movies)
        })
        viewModel.shetel.observe(viewLifecycleOwner, Observer { movies ->
            adapter10.submitList(movies)
        })

        binding.apply {
            barlygy1.setOnClickListener { toSanat(1)}
            barlygy2.setOnClickListener { toSanat(2)}
            barlygy3.setOnClickListener { toSanat(3)}
            barlygy4.setOnClickListener { toSanat(4)}
            barlygy5.setOnClickListener { toSanat(5)}
            barlygy6.setOnClickListener { toSanat(6)}
            barlygy7.setOnClickListener { toSanat(7)}
        }
    }

    private fun initRecyclerViews() {
        initRecyclerView(adapter, binding.rcView)
        initRecyclerView(adapter1, binding.rcView1)
        initRecyclerView(adapter2, binding.rcView2)
        initRecyclerView(adapter3, binding.rcView3)
        initRecyclerView(adapter4, binding.rcView4)
        initRecyclerView(adapter5, binding.rcView5)
        initRecyclerView(adapter6, binding.rcView6)
        initRecyclerView(adapter7, binding.rcView7)
        initRecyclerView(adapter8, binding.rcView8)
        initRecyclerView(adapter9, binding.rcView9)
        initRecyclerView(adapter10, binding.rcView10)
    }

    private fun initRecyclerViewAdapters() {
        adapter = MainPageAdapter(childFragmentManager)
        adapter.setOnItemClickListener(this)
        adapter1 = WatchedMovieAdapter(childFragmentManager)
        adapter1.setOnItemClickListener(this)
        adapter2 = TrendAdapter(childFragmentManager)
        adapter2.setOnItemClickListener(this)
        adapter3 = ForYouAdapter(childFragmentManager)
        adapter3.setOnItemClickListener(this)
        adapter4 = GenresAdapter()
        adapter5 = ZhobalarAdapter(childFragmentManager)
        adapter5.setOnItemClickListener(this)
        adapter6 = RealityAdapter(childFragmentManager)
        adapter6.setOnItemClickListener(this)
        adapter7 = TelehikayaAdapter(childFragmentManager)
        adapter7.setOnItemClickListener(this)
        adapter8 = AgesAdapter()
        adapter9 = DerektiAdapter(childFragmentManager)
        adapter9.setOnItemClickListener(this)
        adapter10 = ShetelAdapter(childFragmentManager)
        adapter10.setOnItemClickListener(this)
    }
    private fun setOffSetToRecView() {
        val cardDecoration = CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt())
        listOf(
            binding.rcView,
            binding.rcView1,
            binding.rcView2,
            binding.rcView3,
            binding.rcView4,
            binding.rcView5,
            binding.rcView6,
            binding.rcView7,
            binding.rcView8,
            binding.rcView9,
            binding.rcView10
        ).forEach { recyclerView ->
            recyclerView.addItemDecoration(cardDecoration)
        }
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, bundle)
    }

    fun toSanat(int : Int){
        val sanattarFragment = SanattarFragment().apply {
            arguments = Bundle().apply { putInt("int", int) }
        }
        findNavController().navigate(R.id.action_homeFragment_to_sanattarFragment, sanattarFragment.arguments)
    }

    override fun onSeasonClick(id: Int) {
        // TODO: Not yet implemented
    }
}
