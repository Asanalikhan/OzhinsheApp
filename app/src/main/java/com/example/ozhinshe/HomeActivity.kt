package com.example.ozhinshe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ozhinshe.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var currentFragment: Fragment? = null

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentFragment = HomeFragment()
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_ -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.bookmark -> replaceFragment(BookmarkFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        if (fragment !== currentFragment) {
            currentFragment = fragment
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commitNow()
        }
    }
}