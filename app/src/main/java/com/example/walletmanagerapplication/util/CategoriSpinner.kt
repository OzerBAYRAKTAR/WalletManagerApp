package com.example.walletmanagerapplication.util

import com.example.walletmanagerapplication.R

data class Categori(val image:Int, val name:String)

object Categories{
    private val images= intArrayOf(
        R.drawable.ic_categori,
        R.drawable.ic_shopping,
        R.drawable.ic_food,
        R.drawable.ic_araba,
        R.drawable.ic_baby,
        R.drawable.ic_beauty,
        R.drawable.ic_egitim,
        R.drawable.ic_eglence,
        R.drawable.ic_elektronik,
        R.drawable.ic_fastfood,
        R.drawable.ic_hayvan,
        R.drawable.ic_icecek,
        R.drawable.ic_konut,
        R.drawable.ic_saglik,
        R.drawable.ic_hediye,
        R.drawable.ic_mobilya,
        R.drawable.ic_sebze,
        R.drawable.ic_onarim,
        R.drawable.ic_phone,
        R.drawable.ic_sigara,
        R.drawable.ic_sosyal,
        R.drawable.ic_spor,
        R.drawable.ic_toplutasima,
        R.drawable.ic_yolculuk
    )
    private val categories= arrayOf(
        "Tüm Kategoriler",
        "Alışveriş",
        "Gıda",
        "Araba",
        "Bebek",
        "Güzellik",
        "Eğitim",
        "Eğlence",
        "Elektronik",
        "FastFood",
        "Evcil Hayvan",
        "Konut",
        "Sağlık",
        "Hediye",
        "Mobilya",
        "Sebze",
        "Onarım",
        "Telefon",
        "Sigara",
        "Sosyal",
        "Spor",
        "Toplu Taşıma",
        "Yolculuk",
    )
    var list: ArrayList<Categori>?=null
    get(){
        if (field!=null)
            return field

        field=ArrayList()
        for (i in images.indices) {
            val imageId= images[i]
            val categoriName= categories[i]

            val categori=Categori(imageId,categoriName)
            field!!.add(categori)
        }
        return field
    }
}