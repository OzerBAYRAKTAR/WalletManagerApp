package com.example.walletmanagerapplication.data.RoomDb

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "ıncome_table")
@Parcelize
data class IncomeModel(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val amount:Double
) : Parcelable {
}