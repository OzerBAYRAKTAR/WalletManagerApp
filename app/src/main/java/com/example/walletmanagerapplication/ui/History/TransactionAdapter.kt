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

class TransactionAdapter(private val listener:OnItemClickListener,
//private var transactions:List<Transaction>
) : ListAdapter<Transaction, TransactionAdapter.TranscationHolder>(DifferentCallback()) {




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
                categoryHistory.text=transaction.category

                when(transaction.category){

                    "Shopping"  -> {
                        binding.imageHistory.setImageResource(R.drawable.shopping)
                    }
                    "Car"  -> {
                        binding.imageHistory.setImageResource(R.drawable.car)
                    }
                    "Baby"  -> {
                        binding.imageHistory.setImageResource(R.drawable.baby)
                    }
                    "Beauty"  -> {
                        binding.imageHistory.setImageResource(R.drawable.beauty)
                    }
                    "Education"  -> {
                        binding.imageHistory.setImageResource(R.drawable.education)
                    }
                    "Electronic"  -> {
                        binding.imageHistory.setImageResource(R.drawable.electronic)
                    }
                    "FastFood"  -> {
                        binding.imageHistory.setImageResource(R.drawable.fastfood)
                    }
                    "PetShop"  -> {
                        binding.imageHistory.setImageResource(R.drawable.pet)
                    }
                    "Drink"  -> {
                        binding.imageHistory.setImageResource(R.drawable.drink)
                    }
                    "House"  -> {
                        binding.imageHistory.setImageResource(R.drawable.home)
                    }
                    "Furniture"  -> {
                        binding.imageHistory.setImageResource(R.drawable.furniture)
                    }
                    "Food"  -> {
                        binding.imageHistory.setImageResource(R.drawable.food)
                    }
                    "Clothes"  -> {
                        binding.imageHistory.setImageResource(R.drawable.clothes)
                    }
                    "Health"  -> {
                        binding.imageHistory.setImageResource(R.drawable.healthcare)
                    }
                    "Cigarette"  -> {
                        binding.imageHistory.setImageResource(R.drawable.smoking)
                    }
                    "Social"  -> {
                        binding.imageHistory.setImageResource(R.drawable.social)
                    }
                    "Travel"  -> {
                        binding.imageHistory.setImageResource(R.drawable.travel)
                    }
                    "Other"  -> {
                        binding.imageHistory.setImageResource(R.drawable.other)
                    }
                }

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
    /*fun setData(transaction:List<Transaction>) {
        this.transactions = transaction
        notifyDataSetChanged()
    }

     */

}