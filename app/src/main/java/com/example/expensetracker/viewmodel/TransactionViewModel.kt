package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.TransactionDatabase
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repo.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    var repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>
    val totalExpense: LiveData<Double>
    val totalIncome: LiveData<Double>
    val totalBalance : LiveData<Double>

    init {
        val dao = TransactionDatabase.getDatabase(application).getTransactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransaction
        totalExpense = repository.totalExpense
        totalIncome = repository.totalIncome
        totalBalance = repository.totalBalance
    }


    fun updateTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(transaction)
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(transaction)
    }

    fun getTransactionById(id: Int) = repository.getDetailsById(id)

    fun getTransactionOfType(type : String) = repository.getAllSingleTransaction(type)


}