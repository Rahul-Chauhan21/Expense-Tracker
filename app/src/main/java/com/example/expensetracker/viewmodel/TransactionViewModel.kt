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

    init {
        val dao = TransactionDatabase.getDatabase(application).getTransactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransaction
        totalExpense = repository.totalExpense
        totalIncome = repository.totalIncome
    }

    /*fun fetchData(): ArrayList<Expense> {
       val list = ArrayList<Expense>()
       for(i in 0 until 10) {
           val dummy = Expense("Abc", "01/11/1999", i.toDouble(), "Food")
           list.add(dummy)
       }
       return list
   }*/

    fun deleteExpense(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(transaction)
    }

}