package com.example.walletmanagerapplication.data.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.walletmanagerapplication.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Transaction::class,IncomeModel::class],
    version = 5,
    exportSchema = true)
abstract class AppDataBase:RoomDatabase() {
    abstract fun transactionDao() : TransactionDao




    class Callback @Inject constructor(
        private val database:Provider<AppDataBase>,
        @ApplicationScope private val  applicationScope: CoroutineScope
    ):RoomDatabase.Callback()
}