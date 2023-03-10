package com.example.walletmanagerapplication.data.RoomDb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*


@Entity(tableName = "transaction_table")
@Parcelize
data class Transaction(
    val category:String,
    val label: String,
    val amount: Double,
    val description: String,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int=0


):Parcelable{
        val createdDataFormatted:String
        get() =DateFormat.getDateInstance().format(created)
    }
