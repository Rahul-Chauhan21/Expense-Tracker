package com.example.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.databinding.FragmentEditTransactionBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.Constants
import com.example.expensetracker.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class EditTransactionFragment : BaseFragment() {

    private lateinit var binding: FragmentEditTransactionBinding
    private val args: EditTransactionFragmentArgs by navArgs()
    private val sharedViewModel: TransactionViewModel by activityViewModels()
    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTransactionBinding.inflate(layoutInflater, container, false)
        val transaction = args.transaction
        initViews()
        loadData(transaction)
        binding.btnSaveTransaction.setOnClickListener { editTransaction() }
        return binding.root
    }

    private fun initViews() = binding.addTransactionLayout.let {
        val transactionTypeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionType
        )
        val categoryTypeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionCategory
        )

        it.etCategory.setAdapter(categoryTypeAdapter)
        it.etTransactionType.setAdapter(transactionTypeAdapter)
        it.inputAmount.prefixText = Currency.getInstance(Locale.getDefault()).symbol

        it.etNote.doOnTextChanged { text, start, count, after ->
            handleNoteTextChange(text)
        }

        it.etDate.setOnClickListener { clickDatePicker() }
    }

    private fun handleNoteTextChange(text: CharSequence?) = binding.addTransactionLayout.let {
        if (text!!.length > 50) {
            it.inputNote.error = "Message Exceeded 50 Characters!"
            binding.btnSaveTransaction.isEnabled = false
        } else if (text.length <= 50) {
            it.inputNote.error = null
            binding.btnSaveTransaction.isEnabled = true
        }
    }

    private fun editTransaction() = binding.addTransactionLayout.let {
        val id = args.transaction.id
        val title = it.etTitle.text.toString()
        val date = it.etDate.text.toString()
        val note = it.etNote.text.toString()
        var amount = it.etAmount.text.toString().toDoubleOrNull()

        val category = it.etCategory.text.toString()
        val transactionType = it.etTransactionType.text.toString()

        if (title.isNotEmpty() && amount != null && transactionType.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty() && note.isNotEmpty()) {

            when (transactionType) {
                "Income" -> {
                }
                "Expense" -> amount = abs(amount) * -1
            }

            sharedViewModel.updateTransaction(
                Transaction(
                    title,
                    amount,
                    transactionType,
                    category,
                    date,
                    note,
                    id
                )
            ).also {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_expense_saved),
                    Toast.LENGTH_SHORT
                ).show().also {
                    findNavController().popBackStack()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Invalid Request, Please Check all the inputs are correct & not empty!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadData(transaction: Transaction) = with(binding) {
        addTransactionLayout.etTitle.setText(transaction.title)
        addTransactionLayout.etAmount.setText(abs(transaction.amount).toString())
        addTransactionLayout.etTransactionType.setText(transaction.transactionType, false)
        addTransactionLayout.etCategory.setText(transaction.category, false)
        addTransactionLayout.etDate.setText(transaction.date)
        addTransactionLayout.etNote.setText(transaction.note)
    }

    private fun clickDatePicker() = with(binding.addTransactionLayout) {
        val date = Calendar.getInstance().time
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, _, _, _ ->
                val dateFormatter = SimpleDateFormat("dd MMM yyyy")
                etDate.setText(dateFormatter.format(date))

            }, year, month, day
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()

    }

    private fun getTransactionContent(): Transaction = binding.addTransactionLayout.let {
        val id = args.transaction.id
        val title = it.etTitle.text.toString()
        var amount = it.etAmount.text.toString().toDouble()
        val transactionType = it.etTransactionType.text.toString()

        val category = it.etCategory.text.toString()
        val date = it.etDate.text.toString()
        val note = it.etNote.text.toString()

        return Transaction(
            title = title,
            amount = amount,
            transactionType = transactionType,
            category = category,
            date = date,
            note = note,
            id = id
        )
    }
}