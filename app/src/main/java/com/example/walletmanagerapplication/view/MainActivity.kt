package com.example.walletmanagerapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.adapter.CategoriArrayAdapter
import com.example.walletmanagerapplication.databinding.ActivityMainBinding
import com.example.walletmanagerapplication.util.Categories
import kotlinx.android.synthetic.main.activity_add_transcation.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setBackgroundResource(androidx.appcompat.R.drawable.abc_item_background_holo_dark)

        setupCustomSpinner()
        intentFab()


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

    private fun setupCustomSpinner() {
        val adapter= CategoriArrayAdapter(this, Categories.list!!)
        customSpinner.adapter=adapter
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment)
        fragmentTransaction.commit()
    }
    private fun intentFab() {
        binding.fabAdd.setOnClickListener {
            val intent=Intent(this,AddTranscationActivity::class.java)
            startActivity(intent)
        }
    }

}