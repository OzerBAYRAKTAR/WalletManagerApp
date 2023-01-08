package com.example.walletmanagerapplication.ui.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.ui.ADD_TRANSACTION_RESULT_OK
import com.example.walletmanagerapplication.ui.EDIT_TRANSACTION_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionDao: TransactionDao
): ViewModel() {

    val query= MutableStateFlow("")

    private val transactionEventChannel=Channel<TransactionEvent>()
    val transactionEvent=transactionEventChannel.receiveAsFlow()
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
        transactionDao.insert(transaction)
    }
    fun addNewTransaction()=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.NavigateToAddSecreen)
    }
    fun onAddEditResult(result:Int){
        when (result) {
            ADD_TRANSACTION_RESULT_OK -> showTransactionSavedMessage("Transaction Added!")
            EDIT_TRANSACTION_RESULT_OK -> showTransactionSavedMessage("Transaction Updated!")
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
    }
}