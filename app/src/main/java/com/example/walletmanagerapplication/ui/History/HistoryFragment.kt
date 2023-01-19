package com.example.walletmanagerapplication.ui.History


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.AppDataBase
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentHistoryBinding
import com.example.walletmanagerapplication.util.WalletUtils
import com.example.walletmanagerapplication.util.WalletUtils.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) , TransactionAdapter.OnItemClickListener {


    private val viewModel: HistoryViewModel by viewModels()

    private var totalExpense:Int=0
    private var totalIncome:Int=0
    private var totalBalance:Int=0
    private lateinit var db:AppDataBase



    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHistoryBinding.bind(view)

        println("-----------------")
        val transactionAdapter=TransactionAdapter(this)


        viewModel.getTotalBalance().observe(viewLifecycleOwner, Observer {
            it?.let{
                totalBalance=it
                binding.totalBalance.text="$$totalBalance"

            }
        })
        viewModel.getExpense().observe(viewLifecycleOwner, Observer {
            it?.let {
                totalExpense=it
                binding.expenseTxt.text="$totalExpense"

            }
        })
        viewModel.getIncome().observe(viewLifecycleOwner, Observer {
            it?.let{
                totalIncome=it
                binding.incomeTxt.text="$totalIncome"

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
                println("$totalExpense")

            }
            fabSalaryAdd.setOnClickListener {
                viewModel.addNewIncome()
            }

            incomeDelete.setOnClickListener {

                val dialogg = Dialog(requireContext())
                dialogg.setCancelable(false)
                dialogg.setContentView(R.layout.delete_dialog)

                val cancelButotn=dialogg.findViewById<Button>(R.id.noDelete)
                val saveButton=dialogg.findViewById<Button>(R.id.yesDelete)

                dialogg.window!!.setGravity(Gravity.CENTER_VERTICAL)

                saveButton.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO){
                            viewModel.deleteIncome()
                        }
                    }
                    dialogg.dismiss()
                    WalletUtils
                }
                cancelButotn.setOnClickListener {
                    dialogg.dismiss()
                }
                dialogg.show()
            }


        }

        viewModel.transaction.observe(viewLifecycleOwner){
            transactionAdapter.submitList(it)
        }
        db= Room.databaseBuilder(requireContext(),
        AppDataBase::class.java,"ıncome_table").build()



        setFragmentResultListener("add_edit_request"){_,bundle ->
            val result=bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.incomeEvent.collect{ incomeEvent ->
                when(incomeEvent) {
                is HistoryViewModel.IncomeEvent.NavigateToIncomeScreen ->{
                    val action=HistoryFragmentDirections.actionHistoryFragmentToIncomeFragment()
                    findNavController().navigate(action)
                     }
                }.exhaustive
            }
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

    }
    private fun deleteAllIncome() {
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            viewModel.deleteIncome()
            Toast.makeText(
                requireContext(),
                "Successfully removed income",
                Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete everything")
        builder.setMessage("Do you want to reset your income ?")
        builder.create().show()
    }


    override fun onItemclick(transaction: Transaction) {
        viewModel.onTransactionSelected(transaction)
    }

}