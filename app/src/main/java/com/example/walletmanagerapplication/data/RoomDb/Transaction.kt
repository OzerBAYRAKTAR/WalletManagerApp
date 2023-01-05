package com.example.walletmanagerapplication.data.RoomDb

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat


@Entity(tableName = "transaction_table")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val category:String,
    val label:String,
    val amount:Double,
    val description:String,
    val created:Long=System.currentTimeMillis(),
    val image:Int
    ):Parcelable{
        val createdDataFormatted:String
        get() =DateFormat.getDateInstance().format(created)
    }
