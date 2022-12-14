package com.example.walletmanagerapplication.data.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun transactionDao() : TransactionDao
}