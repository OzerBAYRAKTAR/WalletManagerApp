package com.example.walletmanagerapplication.ui.Analytics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.repository.ExpenseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
    private val expenseRepo: ExpenseRepo,
    private val state: SavedStateHandle
): ViewModel(){

    fun getTransaction(): Flow<List<Transaction>> = transactionDao.getTransactions()

    fun getTrans(): List<Transaction> = transactionDao.getTrans()

    val transaction=state.get<Transaction>("transaction")

    var transactionCategory=state.get<String>("transactionCategory") ?: transaction?.category ?: ""
        set(value){
            field=value
            state.set("transactionCategory",value)
        }
    var transactionLabel=state.get<String>("transactionLabel") ?: transaction?.label ?: ""
        set(value){
            field=value
            state.set("transactionLabel",value)
        }
    var transactionAmount=state.get<String>("transactionAmount") ?: transaction?.amount ?: ""
        set(value){
            field=value
            state.set("transactionAmount",value)
        }

    //private val transactionFlow=transactionDao.getTransactions()
    //val transaction=transactionFlow.asLiveData()
}