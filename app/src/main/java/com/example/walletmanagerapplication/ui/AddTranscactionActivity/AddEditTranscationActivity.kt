package com.example.walletmanagerapplication.ui.AddTranscactionActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.walletmanagerapplication.databinding.ActivityAddTranscationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTranscationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTranscationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddTranscationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        finishTransaction()

    }

    /* private fun isEmpty() {
        binding.labelInput.addTextChangedListener {
            if (it!!.isNotEmpty())
                labelLayout.error=null
        }
        binding.amountInput.addTextChangedListener {
            if (it!!.isNotEmpty())
                amountLayout.error=null
        }
        buttonAddTransaction.setOnClickListener {
            val label=labelInput.text.toString()
            val amount=amountInput.text.toString().toDoubleOrNull()

            if (label.isEmpty())
                labelLayout.error="Please enter a valid label"

            if (amount==null)
                amountLayout.error="Please enter a valid amount"


        }
    }

     */
    private fun finishTransaction() {
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }
}