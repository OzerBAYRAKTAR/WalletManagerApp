package com.example.walletmanagerapplication.ui.History

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.ItemHistoryBinding

class TransactionAdapter(private val listener:OnItemClickListener) : ListAdapter<Transaction, TransactionAdapter.TranscationHolder>(DifferentCallback()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscationHolder {
        val binding=ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TranscationHolder(binding)
    }

    override fun onBindViewHolder(holder: TranscationHolder, position: Int) {
        val currentItem =getItem(position)
        holder.bind(currentItem)
        //val context=holder.binding.amount.context

        /*if (transcation.amount >= 0) {
            holder.amount.text="+ ₺%.2f".format(transcation.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.green))
        }else{
            holder.amount.text="- ₺%.2f".format(Math.abs(transcation.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        holder.label.text=transcation.label

         */

    }
    inner class TranscationHolder(private val binding:ItemHistoryBinding) :RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener{
                    val position=adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val trans = getItem(position)
                        listener.onItemclick(trans)
                    }
                }
            }
        }

        fun bind(transaction: Transaction) {
            binding.apply {
                amountHistory.text=transaction.amount.toString()

            }
        }
    }
    interface OnItemClickListener{
        fun onItemclick(transaction: Transaction)
    }
    class DifferentCallback: DiffUtil.ItemCallback<Transaction>(){
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction):Boolean{
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction)=
            oldItem==newItem
    }

}