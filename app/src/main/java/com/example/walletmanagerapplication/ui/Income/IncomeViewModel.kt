package com.example.walletmanagerapplication.ui.Income

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walletmanagerapplication.data.RoomDb.IncomeModel
import com.example.walletmanagerapplication.data.RoomDb.TransactionDao
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val state:SavedStateHandle,
    private val transactionDao: TransactionDao
) : ViewModel(){

    val income=state.get<IncomeModel>("income")

    var incomeAmount=state.get<String>("incomeAmount") ?: income?.amountIncome ?: ""
        set(value){
            field=value
            state.set("incomeAmount",value)
        }

    fun saveClick() {
        if (incomeAmount.equals("")) {
            showInvalidInputMessage("Amount cant be empty")
            return
        } else{
            val newIncome=IncomeModel(
                amountIncome = incomeAmount.toString().toDouble())
            createIncome(newIncome)
            showInvalidInputMessage("Amount added succesfully")
        }
    }

    fun createIncome(incomeModel: IncomeModel)=viewModelScope.launch {
        transactionDao.insertIncome(incomeModel)
    }

    fun showInvalidInputMessage(text:String)=viewModelScope.launch {
        incomeTransactionChannel.send(IncomeViewModel.IncomeTransactionEvent.ShowInvalidInputMessage(text))
    }

    private val incomeTransactionChannel=Channel<IncomeTransactionEvent>()


    sealed class IncomeTransactionEvent {
        data class ShowInvalidInputMessage(val msg:String) : IncomeTransactionEvent()

    }

}