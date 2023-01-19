package com.example.walletmanagerapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExpenseRepo @Inject constructor (
    val transactionDao: TransactionDao) {

    fun getTotalExpense():LiveData<Int>{
        return transactionDao.getTotalSpent()
    }

    fun getTotalIncome(): LiveData<Int> {
        return transactionDao.getTotalIncome()
    }
    fun getTotalBalance(): LiveData<Int> {
        return transactionDao.getTotalBalance()
    }

}