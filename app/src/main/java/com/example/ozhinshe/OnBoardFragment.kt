package com.example.ozhinshe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.ozhinshe.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private var titleList = mutableListOf<String>("Фильмдер, телехикаялар, ситкомдар, анимациялық жобалар, телебағдарламалар мен реалити-шоулар, аниме және тағы басқалары", "Кез келген құрылғыдан қара \n" +
            "Сүйікті фильміңді  қосымша төлемсіз телефоннан, планшеттен, ноутбуктан қара", "Тіркелу оңай. Қазір тіркел де қалаған фильміңе қол жеткіз")
    private var imageList = mutableListOf<Int>(R.drawable.onboarding, R.drawable.onboarding2, R.drawable.onboarding3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentOnBoardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = ViewPagerAdapter(titleList, imageList)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = binding.indicator
        indicator.setViewPager(binding.viewPager2)

        binding.btnOtkizu.setOnClickListener {
            binding.viewPager2.apply {
                beginFakeDrag()
                fakeDragBy(-12f)
                endFakeDrag()
            }
        }

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    2 -> {
                        binding.btnOtkizu.visibility = View.GONE
                        binding.button.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btnOtkizu.visibility = View.VISIBLE
                        binding.button.visibility = View.GONE
                    }
                }
            }
        })

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_authorizationFragment)
        }
    }
}