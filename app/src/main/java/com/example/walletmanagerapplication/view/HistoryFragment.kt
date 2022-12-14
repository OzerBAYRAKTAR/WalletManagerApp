package com.example.walletmanagerapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.adapter.TransactionAdapter
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment(R.layout.fragment_history) {
    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter:TransactionAdapter
    private lateinit var linearlayoutManager:LinearLayoutManager
    private var fragmentBinding: FragmentHistoryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHistoryBinding.bind(view)
        fragmentBinding=binding


            transactions= arrayListOf()

        transactionAdapter=TransactionAdapter(transactions)
        linearlayoutManager= LinearLayoutManager(requireContext())

        binding.historyRv.apply {
            adapter=transactionAdapter
            layoutManager=linearlayoutManager

        }




    }

}