package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.adapter.TransactionAdapter
import com.example.expensetracker.databinding.FragmentTransactionsBinding
import com.example.expensetracker.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.util.*


class TransactionsDashboardFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(layoutInflater, container, false)

        val adapter = TransactionAdapter(requireActivity().applicationContext)
        binding.rvTransactionList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(TransactionViewModel::class.java)

        viewModel.allTransactions.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        viewModel.totalIncome.observe(viewLifecycleOwner, { income ->
            if (income != null) displayAmount(amount = income, "income") else displayAmount(
                0.0,
                "income"
            )
        })

        viewModel.totalExpense.observe(viewLifecycleOwner, { expense ->
            if (expense != null) displayAmount(amount = expense, "expense") else displayAmount(
                0.0,
                "expense"
            )
        })
        binding.fabAddTransaction.setOnClickListener { startAddTransactionActivity() }
        return binding.root
    }


    private fun displayAmount(amount: Double, type: String) {
        val formattedTotalAmount = formatAmount(amount)
        var balance = binding.totalBalanceView.totalBalance.text.toString().toDoubleOrNull()
        if (balance == null) balance = 0.0

        if (type == "income") {
            binding.incomeCardView.tvTotalAmount.text =
                getString(R.string.income, formattedTotalAmount)
            balance += amount

        } else {
            binding.expenseCardView.tvTotalAmount.text =
                getString(R.string.expense, formattedTotalAmount)
            balance -= amount
        }

        binding.totalBalanceView.totalBalance.text =
            getString(R.string.balance, formatAmount(balance))

    }

    private fun formatAmount(amount: Double) =
        NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)

    private fun startAddTransactionActivity() {
        val i = Intent(requireActivity(), AddTransactionActivity::class.java)
        startActivity(i)
    }


}