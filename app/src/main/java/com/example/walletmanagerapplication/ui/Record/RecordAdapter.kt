package com.example.walletmanagerapplication.ui.Record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.ItemHistoryBinding
import com.example.walletmanagerapplication.databinding.ItemRecordBinding


class RecordAdapter() : ListAdapter<Transaction, RecordAdapter.TransRecordHolder>(DifferentCallback()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransRecordHolder {
        val binding= ItemRecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransRecordHolder(binding)
    }

    override fun onBindViewHolder(holder: TransRecordHolder, position: Int) {
        val currentItem =getItem(position)
        holder.bind(currentItem)


    }
    inner class TransRecordHolder(private val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener{
                    val position=adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val trans = getItem(position)
                    }
                }
            }
        }

        fun bind(transaction: Transaction) {
            binding.apply {
                recordAmount.text="Amount: $${transaction.amount.toString()}"
                recordCategory.text="Category: ${transaction.category}"
                recordDescription.text="Description: ${transaction.description}"
                recordDate.text="Date: ${transaction.createdDataFormatted}"

                when(transaction.category){

                    "Shopping"  -> {
                        binding.recordImage.setImageResource(R.drawable.shopping)
                    }
                    "Car"  -> {
                        binding.recordImage.setImageResource(R.drawable.car)
                    }
                    "Baby"  -> {
                        binding.recordImage.setImageResource(R.drawable.baby)
                    }
                    "Beauty"  -> {
                        binding.recordImage.setImageResource(R.drawable.beauty)
                    }
                    "Education"  -> {
                        binding.recordImage.setImageResource(R.drawable.education)
                    }
                    "Electronic"  -> {
                        binding.recordImage.setImageResource(R.drawable.electronic)
                    }
                    "FastFood"  -> {
                        binding.recordImage.setImageResource(R.drawable.fastfood)
                    }
                    "PetShop"  -> {
                        binding.recordImage.setImageResource(R.drawable.pet)
                    }
                    "Drink"  -> {
                        binding.recordImage.setImageResource(R.drawable.drink)
                    }
                    "House"  -> {
                        binding.recordImage.setImageResource(R.drawable.home)
                    }
                    "Furniture"  -> {
                        binding.recordImage.setImageResource(R.drawable.furniture)
                    }
                    "Food"  -> {
                        binding.recordImage.setImageResource(R.drawable.food)
                    }
                    "Clothes"  -> {
                        binding.recordImage.setImageResource(R.drawable.clothes)
                    }
                    "Health"  -> {
                        binding.recordImage.setImageResource(R.drawable.healthcare)
                    }
                    "Cigarette"  -> {
                        binding.recordImage.setImageResource(R.drawable.smoking)
                    }
                    "Social"  -> {
                        binding.recordImage.setImageResource(R.drawable.social)
                    }
                    "Travel"  -> {
                        binding.recordImage.setImageResource(R.drawable.travel)
                    }
                    "Other"  -> {
                        binding.recordImage.setImageResource(R.drawable.other)
                    }
                }

            }
        }
    }

    class DifferentCallback: DiffUtil.ItemCallback<Transaction>(){
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction):Boolean{
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction)=
            oldItem==newItem
    }



}