package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.TransactionDatabase
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repo.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TransactionRepository

    init {
        val dao = TransactionDatabase.getDatabase(application).getTransactionDao()
        repository = TransactionRepository(dao)
    }

    fun insertData(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(transaction)
    }
}