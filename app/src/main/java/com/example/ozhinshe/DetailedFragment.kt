package com.example.ozhinshe

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.example.ozhinshe.adapters.ScreenshotAdapter
import com.example.ozhinshe.adapters.UqsasAdapter
import com.example.ozhinshe.data.Item
import com.example.ozhinshe.data.ItemViewModel
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentDetailedBinding
import com.example.ozhinshe.decoration.CardDecoration
import com.example.ozhinshe.modiedata.Movy
import com.example.ozhinshe.modiedata.Screenshot
import com.example.ozhinshe.modiedata.UqsasGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentDetailedBinding
    private lateinit var adapter: UqsasAdapter
    private lateinit var adapter1: ScreenshotAdapter
    private lateinit var itemViewModel: ItemViewModel
    private var isExpanded = false
    private lateinit var viewModel: HomeViewModel
    private var seriesCount:  Int = 0
    private var posterLink: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        return binding.root
    }
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setOffSet()

        val bundle = arguments
        val id = bundle?.getString("key")?.toInt()

        (activity as HomeActivity).hideBottomNavigationView()

        binding.apply {
            moreBtn.setOnClickListener {
                if (id != null) onSeasonClick(id)
            }
            imageButton.setOnClickListener{
                findNavController().popBackStack()
            }
            barlygy.setOnClickListener {
                val sanattarFragment = SanattarFragment().apply {
                    arguments = Bundle().apply {
                        putString("string", "Ұқсас телехикаялар")
                        putBoolean("string1", true)
                    }
                }
                findNavController().navigate(R.id.action_detailedFragment_to_sanattarFragment)
            }
            readMore.setOnClickListener {
                isExpanded = !isExpanded

                if (isExpanded) {
                    textView.maxLines = Int.MAX_VALUE
                    readMore.text = getString(R.string.azyraq)
                    textViewBackground.visibility = View.GONE
                } else {
                    textView.maxLines = 7
                    readMore.text = getString(R.string.tolugraq)
                    textViewBackground.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val item = Item(null, id)
            if (itemViewModel.getItem(item)) {
                binding.imageButton2.setImageResource(R.drawable.bookmark_add_selected)
            } else {
                binding.imageButton2.setImageResource(R.drawable.bookmark_add)
            }
        }

        binding.imageButton2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val item = Item(null, id)
                if (itemViewModel.getItem(item)) {
                    itemViewModel.deleteItem(item)
                    binding.imageButton2.setImageResource(R.drawable.bookmark_add)
                } else {
                    binding.imageButton2.setImageResource(R.drawable.bookmark_add_selected)
                    itemViewModel.addItem(item)
                }
            }
        }

        adapter = UqsasAdapter(childFragmentManager)
        adapter.setOnItemClickListener(this)
        adapter1 = ScreenshotAdapter()
        initRecyclerView(adapter, binding.rcView2)
        initRecyclerView(adapter1, binding.rcView)

        if (id != null) viewModel.setIdDetaied(id)

        viewModel.detailed.observe(viewLifecycleOwner, Observer { responce ->
            binding.cvMovieName.text = responce.name
            binding.cvMovieDesc.text = "" + responce.year + "." + responce.movieType + "." + responce.seasonCount + " сезон," + responce.seriesCount + " серия."
            Glide.with(binding.root).load(responce.poster.link).into(binding.imageView2)
            binding.textView.text = responce.description
            binding.director.text = responce.director
            binding.producer.text = responce.producer
            binding.bolimder.text = "" + responce.seasonCount + " сезон," + responce.seriesCount + " серия"
            posterLink = responce.screenshots[0].link
            seriesCount = responce.seriesCount
        })
        viewModel.uqsasgenre.observe(viewLifecycleOwner, Observer { response ->
            adapter.submitList(response)
        })
        viewModel.screenshot.observe(viewLifecycleOwner, Observer { response ->
            adapter1.submitList(response)
        })
    }

    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
    private fun setOffSet(){
        binding.rcView.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView2.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
    }

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_detailedFragment_self, bundle)
    }

    override fun onSeasonClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putInt("seriesCount", seriesCount)
        bundle.putString("posterLink", posterLink)
        findNavController().navigate(R.id.action_detailedFragment_to_bolimFragment, bundle)
    }
}
