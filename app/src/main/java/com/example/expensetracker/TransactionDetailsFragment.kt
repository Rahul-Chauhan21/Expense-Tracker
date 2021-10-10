package com.example.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.databinding.FragmentTransactionDetailsBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.viewmodel.TransactionViewModel

class TransactionDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentTransactionDetailsBinding
    private val args: TransactionDetailsFragmentArgs by navArgs()
    private val sharedViewModel: TransactionViewModel by activityViewModels()

    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailsBinding.inflate(layoutInflater, container, false)
        val transaction = args.transaction
        binding.fabEditTransaction.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_transactionDetailsFragment_to_editTransactionFragment)
        }
        val transactionDetails = getTransaction(transaction.id)
        transactionDetails.observe(viewLifecycleOwner, {
            it?.let {
                onDetailsLoaded(it)
            }
        })

        return binding.root
    }

    private fun onDetailsLoaded(transaction: Transaction) = with(binding.transactionDetails) {
        title.text = transaction.title
        amount.text = formatAmount(transaction.amount)
        type.text = transaction.transactionType
        category.text = transaction.category
        date.text = transaction.date
        note.text = transaction.note

        binding.fabEditTransaction.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("transaction", transaction)
            }
            findNavController().navigate(
                R.id.action_transactionDetailsFragment_to_editTransactionFragment, bundle
            )
        }

        binding.fabAddReminder.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("transaction", transaction)
            }
            findNavController().navigate(
                R.id.action_transactionDetailsFragment_to_reminderFragment,
                bundle
            )
        }
    }

    private fun getTransaction(id: Int) = sharedViewModel.getTransactionById(id)

}