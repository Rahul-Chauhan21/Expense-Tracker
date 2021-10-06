package com.example.expensetracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("Select * from transactions_table order by id ASC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE transactionType == :transactionType")
    fun getAllSingleTransaction(transactionType: String): LiveData<List<Transaction>>

    @Query("Select SUM(amount) from transactions_table where transactionType = :search_query ")
    fun getTotalIncome(search_query: String = "Income"): LiveData<Double>

    @Query("Select SUM(amount) from transactions_table where transactionType = :search_query")
    fun getTotalExpense(search_query: String = "Expense"): LiveData<Double>

    // get single transaction by id
    @Query("SELECT * FROM transactions_table WHERE id = :id")
    fun getTransactionByID(id: Int): LiveData<Transaction>

    @Query("Select sum(amount) from transactions_table")
    fun getBalance(): LiveData<Double>


    /*@Query("Select * from transactions_table where category = :search_query")
    fun filterExpensesBasedOnCategory(search_query : String?) : LiveData<List<Transaction>>*/

    /*@Query("Select category from (select category, count(category) from expense_table order by count(category) desc limit 1)")
    fun getFavouriteCategory() : LiveData<String>*/


}