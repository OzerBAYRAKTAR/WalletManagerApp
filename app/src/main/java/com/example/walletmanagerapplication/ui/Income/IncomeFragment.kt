package com.example.walletmanagerapplication.ui.Income

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.databinding.FragmentAddIncomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncomeFragment @Inject constructor(): Fragment(R.layout.fragment_add_income){
    private val viewModel:IncomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentAddIncomeBinding.bind(view)

        binding.apply {
            amountIncomeInput.setText(viewModel.incomeAmount.toString())

            amountIncomeInput.addTextChangedListener {
                viewModel.incomeAmount=it.toString()
            }

            saveIncome.setOnClickListener {
                viewModel.saveClick()
                findNavController().popBackStack()
            }
        }
    }
}