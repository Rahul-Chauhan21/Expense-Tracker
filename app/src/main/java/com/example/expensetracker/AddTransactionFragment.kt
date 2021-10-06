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
import com.example.expensetracker.databinding.FragmentAddTransactionBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.Constants
import com.example.expensetracker.viewmodel.AddTransactionViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class AddTransactionFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private val sharedViewModel: AddTransactionViewModel by activityViewModels()
    private lateinit var binding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        initViews()
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

        binding.btnSaveTransaction.setOnClickListener { submitAddTransaction() }

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

    private fun submitAddTransaction() = binding.addTransactionLayout.let {
        val title = it.etTitle.text.toString()
        val date = it.etDate.text.toString()
        val note = it.etNote.text.toString()
        var amount = it.etAmount.text.toString().toDoubleOrNull()

        val category = it.etCategory.text.toString()
        val transactionType = it.etTransactionType.text.toString()

        if (note.isNotEmpty() && amount != null && title.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty() && transactionType.isNotEmpty()) {
            when (transactionType) {
                "Income" -> {
                }
                "Expense" -> amount = abs(amount) * -1
            }
            sharedViewModel.insertData(
                Transaction(
                    title,
                    amount,
                    transactionType,
                    category,
                    date,
                    note
                )
            ).also {
                Toast.makeText(requireContext(), "$title Inserted", Toast.LENGTH_SHORT).show()
                    .also {
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


}