package com.example.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
class Transaction(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "amount")
    var amount: Double,
    @ColumnInfo(name = "transactionType")
    var transactionType: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "note")
    var note: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
