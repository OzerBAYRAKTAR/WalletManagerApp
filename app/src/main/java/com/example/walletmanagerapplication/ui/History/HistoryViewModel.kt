package com.example.walletmanagerapplication.ui.History

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.IncomeModel
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.repository.ExpenseRepo
import com.example.walletmanagerapplication.ui.ADD_TRANSACTION_RESULT_OK
import com.example.walletmanagerapplication.ui.EDIT_TRANSACTION_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
    private val expenseRepo:ExpenseRepo,
): ViewModel() {



    private val transactionEventChannel=Channel<TransactionEvent>()
    val transactionEvent=transactionEventChannel.receiveAsFlow()

    private val incomeEventChannel=Channel<IncomeEvent>()
    val incomeEvent=incomeEventChannel.receiveAsFlow()


    private val transactionFlow=transactionDao.getTransactions()
    val transaction=transactionFlow.asLiveData()






    fun onTransactionSelected(transaction: Transaction)=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.NavigateToEditScreen(transaction))
    }

    fun onTransactionSwipted(transaction: Transaction)=viewModelScope.launch {
        transactionDao.delete(transaction)
        transactionEventChannel.send(TransactionEvent.ShowUndoDeleteTaskMessage(transaction))
    }
    fun onUndoDeleteClick(transaction: Transaction)=viewModelScope.launch {
        transactionDao.insertTransaction(transaction)
    }
    fun addNewTransaction()=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.NavigateToAddSecreen)
    }

    fun addNewIncome()=viewModelScope.launch {
        incomeEventChannel.send(IncomeEvent.NavigateToIncomeScreen)

    }
    fun onAddEditResult(result:Int){
        when (result) {
            ADD_TRANSACTION_RESULT_OK -> showTransactionSavedMessage("Transaction Added!")
            EDIT_TRANSACTION_RESULT_OK -> showTransactionSavedMessage("Transaction Updated!")
        }
    }
    fun getExpense(): LiveData<Int> {
        return expenseRepo.getTotalExpense()
    }
    fun getIncome(): LiveData<Int> {
        return expenseRepo.getTotalIncome()
    }
    fun getTotalBalance(): LiveData<Int> {
        return expenseRepo.getTotalBalance()
    }
     fun deleteIncome() {
         viewModelScope.launch(Dispatchers.IO) {
             transactionDao.deleteIncome()
         }
     }




    private fun showTransactionSavedMessage(text:String)=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.ShowTaskSavedMessage(text))
    }

    //represent kind of events that we can able use in fragment
    sealed class TransactionEvent{
        object NavigateToAddSecreen:TransactionEvent()
        data class NavigateToEditScreen(val transaction:Transaction):TransactionEvent()
        data class ShowUndoDeleteTaskMessage(val transaction:Transaction):TransactionEvent()
        data class ShowTaskSavedMessage(val msg:String):TransactionEvent()
        data class ShowInvalidInputMessage(val msg:String) : HistoryViewModel.TransactionEvent()
    }
    sealed class IncomeEvent{
        object NavigateToIncomeScreen:IncomeEvent()
    }
}