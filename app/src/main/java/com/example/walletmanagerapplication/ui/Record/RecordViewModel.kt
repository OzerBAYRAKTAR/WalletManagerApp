package com.example.walletmanagerapplication.ui.Record

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.repository.ExpenseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecordViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
    private val expenseRepo: ExpenseRepo,
    private val state: SavedStateHandle,
): ViewModel(){

    fun getExpense(): LiveData<Int> {
        return expenseRepo.getTotalExpense()
    }

    private val transactionFlow=transactionDao.getTransactions()
    val transaction=transactionFlow.asLiveData()
}