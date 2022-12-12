package com.example.walletmanagerapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walletmanagerapplication.data.model.Transaction
import com.example.walletmanagerapplication.databinding.ItemHistoryBinding
import com.example.walletmanagerapplication.util.glideImage

class TransactionAdapter(private val transactions:ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TranscationHolder>() {



   class TranscationHolder(private val binding:ItemHistoryBinding) :RecyclerView.ViewHolder(binding.root){

    fun bind(item: Transaction) {
        with(binding) {
            with(item) {
                labelHistory.text=label
                amountHistory.text=amount.toString()
                imageHistory.glideImage(image)

            }
        }
    }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscationHolder {
        val binding=ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TranscationHolder(binding)
    }

    override fun onBindViewHolder(holder: TranscationHolder, position: Int) {
        val transcation =transactions[position]
    }

    override fun getItemCount(): Int {
        return transactions.size

    }
}