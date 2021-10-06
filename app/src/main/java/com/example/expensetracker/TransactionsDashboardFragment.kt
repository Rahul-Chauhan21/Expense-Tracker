package com.example.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.adapter.TransactionAdapter
import com.example.expensetracker.databinding.FragmentTransactionsBinding
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.viewmodel.TransactionViewModel


class TransactionsDashboardFragment : BaseFragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private val sharedViewModel: TransactionViewModel by activityViewModels()
    private lateinit var adapter: TransactionAdapter
    override var bottomNavigationViewVisibility = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(layoutInflater, container, false)
        setupRv()
        observeTransactions()

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("transaction", it)
            }

            findNavController().navigate(
                R.id.action_transactionsFragment_to_transactionDetailsFragment,
                bundle
            )
        }

        binding.fabAddTransaction.setOnClickListener { findNavController().navigate(R.id.action_transactionsFragment_to_addTransactionFragment) }
        observeChangeType()
        return binding.root
    }

    private fun observeChangeType() {
        binding.expenseCardView.totalCardView.setOnClickListener {
            sharedViewModel.getTransactionOfType("Expense").observe(viewLifecycleOwner, {
                adapter.updateList(it)
            })
        }
        binding.incomeCardView.totalCardView.setOnClickListener {
            sharedViewModel.getTransactionOfType("Income").observe(viewLifecycleOwner, {
                adapter.updateList(it)
            })
        }
        binding.totalBalanceView.totalBalanceCard.setOnClickListener {
            sharedViewModel.allTransactions.observe(viewLifecycleOwner, { list ->
                list?.let {
                    adapter.updateList(it)
                }
            })
        }
    }

    private fun observeTransactions() = with(sharedViewModel) {
        sharedViewModel.allTransactions.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        sharedViewModel.totalIncome.observe(viewLifecycleOwner, { income ->
            if (income != null) displayAmount(amount = income, "income") else displayAmount(
                0.0,
                "income"
            )
        })

        sharedViewModel.totalExpense.observe(viewLifecycleOwner, { expense ->
            if (expense != null) displayAmount(amount = expense, "expense") else displayAmount(
                0.0,
                "expense"
            )
        })
        sharedViewModel.totalBalance.observe(viewLifecycleOwner, { balance ->
            if (balance != null) displayAmount(
                amount = balance,
                type = "balance"
            ) else displayAmount(0.0, type = "balance")
        })
    }


    private fun setupRv() = with(binding) {
        adapter = TransactionAdapter(requireActivity().applicationContext)
        rvTransactionList.adapter = adapter
    }


    private fun displayAmount(amount: Double, type: String) = with(binding) {
        val formattedTotalAmount = formatAmount(amount)
        when (type) {
            "income" -> {

                incomeCardView.tvTotalAmount.text =
                    getString(R.string.income, formattedTotalAmount)

            }

            "expense" -> {
                expenseCardView.tvTotalAmount.text =
                    getString(R.string.expense, formattedTotalAmount)
            }
            else -> {

                totalBalanceView.tvTotalBalance.text =
                    getString(R.string.balance, formattedTotalAmount)


            }
        }
    }
}