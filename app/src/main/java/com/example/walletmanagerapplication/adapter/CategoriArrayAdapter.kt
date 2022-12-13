package com.example.walletmanagerapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.util.Categori
import kotlinx.android.synthetic.main.spinner_item.view.*

class CategoriArrayAdapter(context : Context, categoriList: List<Categori>) : ArrayAdapter<Categori>(context, 0, categoriList) {



    //getview defines the appearance of the spinner item when it is in closed state
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)!!
    }

    //getDropDownView defines the appearance of spinner item when it is opened
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return initView(position,convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val categori = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_item,parent,false)

        view.spinnerImage.setImageResource(categori!!.image)
        view.spinnerText.text=categori.name

        return view


    }
}

