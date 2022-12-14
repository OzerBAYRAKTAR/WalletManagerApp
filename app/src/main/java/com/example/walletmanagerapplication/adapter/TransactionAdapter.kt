package com.example.walletmanagerapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction

class TransactionAdapter(private val transactions:ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TranscationHolder>() {



   class TranscationHolder(view : View) :RecyclerView.ViewHolder(view){
       val label=view.findViewById<TextView>(R.id.labelHistory)
       val amount=view.findViewById<TextView>(R.id.amountHistory)
       val image=view.findViewById<ImageView>(R.id.imageHistory)

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscationHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)
        return TranscationHolder(view)
    }

    override fun onBindViewHolder(holder: TranscationHolder, position: Int) {
        val transcation =transactions[position]
        val context=holder.amount.context

        if (transcation.amount >= 0) {
            holder.amount.text="+ ₺%.2f".format(transcation.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.green))
        }else{
            holder.amount.text="- ₺%.2f".format(Math.abs(transcation.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        holder.label.text=transcation.label
        holder.image.setImageDrawable(context.getDrawable(transcation.image))
    }

    override fun getItemCount(): Int {
        return transactions.size

    }
}