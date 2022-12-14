package com.example.walletmanagerapplication.data.RoomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name="labelInfo")
    val label:String,

    val amount:Double,
    val description:String,
    val image:Int
    )
