package com.example.walletmanagerapplication.ui.History

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.AppDataBase
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var transactions: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearlayoutManager:LinearLayoutManager
    private var fragmentBinding: FragmentHistoryBinding? = null
    private lateinit var roomdb: AppDataBase


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHistoryBinding.bind(view)
        fragmentBinding=binding


            transactions= arrayListOf()

        transactionAdapter= TransactionAdapter(transactions)
        linearlayoutManager= LinearLayoutManager(requireContext())

        binding.historyRv.apply {
            adapter=transactionAdapter
            layoutManager=linearlayoutManager

        }




    }

}