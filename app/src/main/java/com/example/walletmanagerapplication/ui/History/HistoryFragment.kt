package com.example.walletmanagerapplication.ui.History

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentHistoryBinding
import com.example.walletmanagerapplication.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) , TransactionAdapter.OnItemClickListener {


    private val viewModel: HistoryViewModel by viewModels()

    private var totalExpense=0




    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHistoryBinding.bind(view)

        val transactionAdapter=TransactionAdapter(this)

        viewModel.getExpense().observe(viewLifecycleOwner, Observer {
            it?.let {
                totalExpense=it
                binding.expenseTxt.text="$$totalExpense"
            }
        })



        binding.apply {
            historyRv.apply {
                adapter=transactionAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val transaction=transactionAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTransactionSwipted(transaction)
                }

            }).attachToRecyclerView(historyRv)

            fabAddHistory.setOnClickListener {
                viewModel.addNewTransaction()
            }
        }

        viewModel.transaction.observe(viewLifecycleOwner){
            transactionAdapter.submitList(it)
        }





        setFragmentResultListener("add_edit_request"){_,bundle ->
            val result=bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }



        //when fragment is on background we dont listen any event.
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.transactionEvent.collect{event ->
                when(event){
                    is HistoryViewModel.TransactionEvent.ShowUndoDeleteTaskMessage ->{
                        Snackbar.make(requireView(), "Transaciton Deleted",Snackbar.LENGTH_LONG)
                            .setAction("Undo Delete"){
                                viewModel.onUndoDeleteClick(event.transaction)
                            }.show()
                    }
                    is HistoryViewModel.TransactionEvent.NavigateToAddSecreen ->{
                        val action=HistoryFragmentDirections.actionHistoryFragmentToAddEditTranscationFragment(
                            "New Transaction",
                            null
                        )
                        findNavController().navigate(action)
                    }
                    is HistoryViewModel.TransactionEvent.NavigateToEditScreen ->{
                        val action=HistoryFragmentDirections.actionHistoryFragmentToAddEditTranscationFragment(
                            "Update Transaction",
                            event.transaction
                        )
                        findNavController().navigate(action)
                    }
                    is HistoryViewModel.TransactionEvent.ShowTaskSavedMessage ->{
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    else -> {}
                }.exhaustive
            }
        }


        binding.fabSalaryAdd.setOnClickListener {
            val dialogg =Dialog(requireContext())
            dialogg.setCancelable(false)
            dialogg.setContentView(R.layout.add_income)
            //val mDialogview=LayoutInflater.from(requireContext()).inflate(R.layout.add_income,null)

            val amount=dialogg.findViewById(R.id.amountInput) as EditText
            val cancelButotn=dialogg.findViewById(R.id.cancelIncome) as Button
            val saveButton=dialogg.findViewById(R.id.saveIncome) as Button

            dialogg.window!!.setGravity(Gravity.CENTER_VERTICAL)
            

            saveButton.setOnClickListener {
                var amountEdt=amount.text.toString().trim()
                viewModel.incomeOnSaveClick()


            }
            cancelButotn.setOnClickListener {
                dialogg.dismiss()
            }
                dialogg.show()
        }


    }



    override fun onItemclick(transaction: Transaction) {
        viewModel.onTransactionSelected(transaction)
    }

}