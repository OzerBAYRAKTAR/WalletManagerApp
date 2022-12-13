package com.example.walletmanagerapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.adapter.TransactionAdapter
import com.example.walletmanagerapplication.data.model.Transaction
import com.example.walletmanagerapplication.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment(R.layout.fragment_history) {
    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter:TransactionAdapter
    private lateinit var linearlayoutManager:LinearLayoutManager
    private var fragmentBinding: FragmentHistoryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHistoryBinding.bind(view)
        fragmentBinding=binding


            transactions= arrayListOf(
                Transaction("Aylık Maaş",5000.00,R.drawable.ic_add),
                Transaction("HAYVAN MAMASI",-400.00,R.drawable.ic_hayvan),
                Transaction("ARABA YIKAMA",-500.00,R.drawable.ic_araba),
                Transaction("İNGİLİZCE SETİ",-320.00,R.drawable.ic_egitim),
                Transaction("HAFTASONU PARTİSİ",-660.00,R.drawable.ic_eglence),
                Transaction("İÇECEK KOLA",-400.00,R.drawable.ic_icecek),
                Transaction("İÇECEK Fanta",-32.00,R.drawable.ic_icecek),
                Transaction("İÇECEK Ayran",-25.00,R.drawable.ic_icecek),
                Transaction("İÇECEK Şeker",-55.00,R.drawable.ic_icecek),
            )

        transactionAdapter=TransactionAdapter(transactions)
        linearlayoutManager= LinearLayoutManager(requireContext())

        binding.historyRv.apply {
            adapter=transactionAdapter
            layoutManager=linearlayoutManager

        }




    }

}