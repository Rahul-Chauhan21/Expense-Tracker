package com.example.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.databinding.ActivityAddTransactionBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.Constants
import com.example.expensetracker.viewmodel.AddTransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    lateinit var viewModel: AddTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(AddTransactionViewModel::class.java)

        initViews()
    }

    private fun initViews() = binding.addTransactionLayout.let {
        val transactionTypeAdapter = ArrayAdapter(
            this@AddTransactionActivity,
            R.layout.item_autocomplete_layout,
            Constants.transactionType
        )
        val categoryTypeAdapter = ArrayAdapter(
            this@AddTransactionActivity,
            R.layout.item_autocomplete_layout,
            Constants.transactionCategory
        )

        it.etCategory.setAdapter(categoryTypeAdapter)
        it.etTransactionType.setAdapter(transactionTypeAdapter)

        it.etNote.doOnTextChanged { text, start, count, after ->
            handleNoteTextChange(text)
        }

        binding.btnSaveTransaction.setOnClickListener { submitAddExpense() }

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

    private fun submitAddExpense() = binding.addTransactionLayout.let {
        val title = it.etTitle.text.toString()
        val date = it.etDate.text.toString()
        val note = it.etNote.text.toString()
        val amount = it.etAmount.text.toString().toDoubleOrNull()
        val category = it.etCategory.text.toString()
        val transactionType = it.etTransactionType.text.toString()

        if (note.isNotEmpty() && amount != null && title.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty() && transactionType.isNotEmpty()) {
            Toast.makeText(this, "$title Inserted", Toast.LENGTH_SHORT).show()
            viewModel.insertData(Transaction(title, amount, transactionType, category, date, note))
            this.finish()
        } else {
            Toast.makeText(
                this,
                "Invalid Request, Please Check all the inputs are correct & not empty!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clickDatePicker() {
        val date = Calendar.getInstance().time
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, _, _, _ ->
                val dateFormatter = SimpleDateFormat("dd MMM yyyy")

                val tvSelectedDate = findViewById<TextView>(R.id.et_date)
                tvSelectedDate.text = dateFormatter.format(date)

            }, year, month, day
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()

    }
}