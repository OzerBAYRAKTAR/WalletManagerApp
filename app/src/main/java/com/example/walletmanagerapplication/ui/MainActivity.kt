package com.example.walletmanagerapplication.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.databinding.ActivityMainBinding
import com.example.walletmanagerapplication.ui.AddEditTranscactionFragment.AddEditTransactionViewModel
import com.example.walletmanagerapplication.ui.AddEditTranscactionFragment.AddEditTranscationFragment
import com.example.walletmanagerapplication.ui.Analytics.AnalyticsFragment
import com.example.walletmanagerapplication.ui.History.HistoryFragment
import com.example.walletmanagerapplication.ui.Profile.ProfileFragment
import com.example.walletmanagerapplication.ui.Record.RecordFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setBackgroundResource(androidx.appcompat.R.drawable.abc_item_background_holo_dark)

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

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment)
        fragmentTransaction.commit()
    }
    private fun intentFab() {
        binding.fabAdd.setOnClickListener {
            val intent=Intent(this, AddEditTranscationFragment::class.java)
            startActivity(intent)
        }
    }

}
const val ADD_TASK_RESULT_OK= Activity.RESULT_FIRST_USER
const val EDIT_TASK_RESULT_OK= Activity.RESULT_FIRST_USER + 1