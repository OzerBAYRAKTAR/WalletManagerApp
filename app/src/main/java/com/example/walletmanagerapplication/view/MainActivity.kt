package com.example.walletmanagerapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setBackgroundResource(androidx.appcompat.R.drawable.abc_item_background_holo_dark)




        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){

                R.id.historyFragment -> replaceFragment(HistoryFragment())
                R.id.analyticsFragment -> replaceFragment(AnalyticsFragment())
                R.id.recordFragment -> replaceFragment(RecordFragment())
                R.id.profileFragment -> replaceFragment(ProfileFragment())

            }
            true
        }

    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment)
        fragmentTransaction.commit()
    }
}