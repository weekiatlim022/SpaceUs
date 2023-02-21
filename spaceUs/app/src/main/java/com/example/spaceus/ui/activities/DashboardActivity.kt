package com.example.spaceus.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.spaceus.R
import com.example.spaceus.databinding.ActivityDashboardBinding
import com.example.spaceus.ui.fragments.HomeFragment
import com.example.spaceus.ui.fragments.SearchFragment
import com.example.spaceus.ui.fragments.SettingsFragment

class DashboardActivity : BaseActivity() {
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = findViewById(R.id.nav_view)
        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val settingsFragment = SettingsFragment()

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                .commit()
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> setCurrentFragment(homeFragment)
                R.id.navigation_search -> setCurrentFragment(searchFragment)
                R.id.navigation_settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(Fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,Fragment)
            commit()
        }
    }

    override fun onBackPressed(){
        doubleBackToExit()
    }
}