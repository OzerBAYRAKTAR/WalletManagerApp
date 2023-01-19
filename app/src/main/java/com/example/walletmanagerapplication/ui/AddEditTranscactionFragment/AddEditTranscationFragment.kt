package com.example.walletmanagerapplication.ui.AddEditTranscactionFragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.databinding.FragmentAddEditTranscationBinding
import com.example.walletmanagerapplication.util.WalletUtils
import com.example.walletmanagerapplication.util.WalletUtils.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditTranscationFragment @Inject constructor(

) : Fragment(R.layout.fragment_add_edit_transcation){
    private var utils=WalletUtils

    private val viewModel: AddEditTransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentAddEditTranscationBinding.bind(view)


        val categories=resources.getStringArray(R.array.categories)

        val categoryAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,categories)
        categoryAdapter.notifyDataSetChanged()
        binding.spinnerTransaction.adapter=categoryAdapter





        binding.spinnerTransaction.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                binding.categoryText.setText(p0?.getItemAtPosition(p2).toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.apply {
            categoryText.setText(viewModel.transactionCategory)
            labelInput.setText(viewModel.transactionLabel)
            descriptionInput.setText(viewModel.transactionDescription)
            amountInput.setText(viewModel.transactionAmount.toString())
            createdTxt.isVisible=viewModel.transaction !=null
            createdTxt.text="Created: ${viewModel.transaction?.createdDataFormatted}"

            labelInput.addTextChangedListener {
                viewModel.transactionLabel=it.toString()
            }
            categoryText.addTextChangedListener {
                viewModel.transactionCategory=it.toString()
            }

            descriptionInput.addTextChangedListener {
                viewModel.transactionDescription=it.toString()
            }
            amountInput.addTextChangedListener {
                viewModel.transactionAmount=it.toString()
            }

            buttonAddTransaction.setOnClickListener {
                viewModel.onSaveClick()
                findNavController().popBackStack()

            }
            buttonCancelTransaction.setOnClickListener {
                activity?.onBackPressed()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.addEditTransactionEvent.collect{ event->
                when(event){
                    is AddEditTransactionViewModel.AddEditTransactionEvent.NavigateBackWithResult ->{
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditTransactionViewModel.AddEditTransactionEvent.ShowInvalidInputMessage ->{
                        Snackbar.make(requireView(),event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }


}
