package com.example.walletmanagerapplication.ui.Record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentRecordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class RecordFragment : Fragment(R.layout.fragment_record) ,RecordAdapter.OnItemClickListener {

    private val viewModel: RecordViewModel by viewModels()
    private var totalExpense=0

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentRecordBinding.bind(view)

        val transactionAdapter=RecordAdapter(this)

        viewModel.getExpense().observe(viewLifecycleOwner, Observer {
            it?.let {
                totalExpense=it
                binding.recordTotalSpent.text="Total Spent: $$totalExpense"
            }
        })

        binding.apply {
            recordRecycler.apply {
                adapter=transactionAdapter
                layoutManager=LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.transaction.observe(viewLifecycleOwner){
            transactionAdapter.submitList(it)
        }



    }

    override fun onItemclick(transaction: Transaction) {
        TODO("Not yet implemented")
    }

}