package com.example.walletmanagerapplication.ui.AddEditTranscactionFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import com.example.walletmanagerapplication.ui.ADD_TASK_RESULT_OK
import com.example.walletmanagerapplication.ui.EDIT_TASK_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val transactionDao: TransactionDao
): ViewModel(){

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
    var transactionDescription=state.get<String>("transactionDescription") ?: transaction?.description ?: ""
        set(value){
            field=value
            state.set("transactionDescription",value)
        }

    private val addEditTransactionChannel=Channel<AddEditTransactionEvent>()
    val addEditTransactionEvent=addEditTransactionChannel.receiveAsFlow()

    fun onSaveClick() {
        if (transactionCategory.isBlank()) {
            showInvalidInputMessage("Can not be Empty")
            return
        }
        if (transactionAmount.equals("0")) {
            showInvalidInputMessage("Can not be Empty")
            return
        }
        if (transactionLabel.isBlank()) {
            showInvalidInputMessage("Can not be Empty")
            return
        }
        if (transaction != null) {
            val updateTransaction=transaction.copy(
                category = transactionCategory,
                label = transactionLabel,
                amount = transactionAmount.toString().toDouble(),
                description = transactionDescription)
            updateTransaction(updateTransaction)
        }else  {
            val newTransaction=Transaction(
                category = transactionCategory,
                label = transactionLabel,
                amount = transactionAmount.toString().toDouble(),
                description = transactionDescription)
            createTransaction(newTransaction)
        }
    }

    fun updateTransaction(transaction: Transaction)=viewModelScope.launch {
        transactionDao.update(transaction)
        addEditTransactionChannel.send(AddEditTransactionEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }
    fun createTransaction(transaction: Transaction)=viewModelScope.launch {
        transactionDao.insert(transaction)
        addEditTransactionChannel.send(AddEditTransactionEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }
    fun showInvalidInputMessage(text:String)=viewModelScope.launch {
        addEditTransactionChannel.send(AddEditTransactionEvent.ShowInvalidInputMessage(text))
    }


    sealed class AddEditTransactionEvent {
        data class ShowInvalidInputMessage(val msg:String) : AddEditTransactionEvent()
        data class NavigateBackWithResult(val result:Int) : AddEditTransactionEvent()
    }
}