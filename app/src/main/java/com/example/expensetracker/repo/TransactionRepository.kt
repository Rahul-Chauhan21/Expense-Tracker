package com.example.expensetracker.repo

import androidx.lifecycle.LiveData
import com.example.expensetracker.data.local.TransactionDao
import com.example.expensetracker.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransaction: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    val totalExpense: LiveData<Double> = transactionDao.getTotalExpense()
    val totalIncome: LiveData<Double> = transactionDao.getTotalIncome()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
}