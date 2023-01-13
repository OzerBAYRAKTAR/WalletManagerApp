package com.example.walletmanagerapplication.ui.History

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.IncomeDao
import com.example.walletmanagerapplication.data.RoomDb.IncomeModel
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.repository.ExpenseRepo
import com.example.walletmanagerapplication.ui.ADD_TRANSACTION_RESULT_OK
import com.example.walletmanagerapplication.ui.EDIT_TRANSACTION_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
    private val incomeDao: IncomeDao,
    private val expenseRepo:ExpenseRepo,
    private val state: SavedStateHandle,
): ViewModel() {

    val income=state.get<IncomeModel>("income")

    var incomeAmount=state.get<String>("incomeAmount") ?: income?.amount ?: ""
        set(value){
            field=value
            state.set("incomeAmount",value)
        }


    private val transactionEventChannel=Channel<TransactionEvent>()
    val transactionEvent=transactionEventChannel.receiveAsFlow()

    private val transactionFlow=transactionDao.getTransactions()
    val transaction=transactionFlow.asLiveData()


    fun incomeOnSaveClick() {
        if (incomeAmount.equals("0")) {
            showInvalidInputMessage("Amount cant be empty")
            return
        }
        if (income == null) {
            val newIncome=IncomeModel(
                amount = incomeAmount.toString().toDouble())
            createIncome(newIncome)

        }

    }
    fun createIncome(income: IncomeModel)=viewModelScope.launch {
        incomeDao.insertIncome(income)
    }

    fun onTransactionSelected(transaction: Transaction)=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.NavigateToEditScreen(transaction))
    }
    fun onTransactionSwipted(transaction: Transaction)=viewModelScope.launch {
        transactionDao.deleteTransaction(transaction)
        transactionEventChannel.send(TransactionEvent.ShowUndoDeleteTaskMessage(transaction))
    }
    fun onUndoDeleteClick(transaction: Transaction)=viewModelScope.launch {
        transactionDao.insertTransaction(transaction)
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
    fun getExpense(): LiveData<Int> {
        return expenseRepo.getTotalExpense()
    }

    private fun showTransactionSavedMessage(text:String)=viewModelScope.launch {
        transactionEventChannel.send(TransactionEvent.ShowTaskSavedMessage(text))
    }
    fun showInvalidInputMessage(text:String)=viewModelScope.launch {
        transactionEventChannel.send(HistoryViewModel.TransactionEvent.ShowInvalidInputMessage(text))
    }

    //represent kind of events that we can able use in fragment
    sealed class TransactionEvent{
        object NavigateToAddSecreen:TransactionEvent()
        data class NavigateToEditScreen(val transaction:Transaction):TransactionEvent()
        data class ShowUndoDeleteTaskMessage(val transaction:Transaction):TransactionEvent()
        data class ShowTaskSavedMessage(val msg:String):TransactionEvent()
        data class ShowInvalidInputMessage(val msg:String) : HistoryViewModel.TransactionEvent()
    }
}