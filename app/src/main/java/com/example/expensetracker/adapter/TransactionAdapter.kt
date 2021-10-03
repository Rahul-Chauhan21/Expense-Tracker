package com.example.expensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ItemTransactionLayoutBinding
import com.example.expensetracker.model.Transaction
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransactionAdapter(private val context: Context) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val allTransactions = ArrayList<Transaction>()

    inner class TransactionViewHolder(val binding: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        with(holder) {
            with(allTransactions[position]) {
                binding.ivTransaction.setImageResource(R.drawable.ic_others)
                binding.tvTitle.text = this.title
                binding.tvCategory.text = this.category
                if (this.transactionType == "Income") {
                    binding.tvAmount.text =
                        context.resources.getString(R.string.income, formatAmount(this.amount))
                    binding.tvAmount.setTextColor(context.resources.getColor(R.color.income))
                } else {
                    binding.tvAmount.text =
                        context.resources.getString(R.string.expense, formatAmount(this.amount))
                    binding.tvAmount.setTextColor(context.resources.getColor(R.color.expense))
                }

                binding.tvDate.text = this.date
            }
        }
    }

    override fun getItemCount() = allTransactions.size

    fun updateList(newList: List<Transaction>) {
        allTransactions.clear()
        allTransactions.addAll(newList)

        notifyDataSetChanged()
    }

    private fun formatAmount(amount: Double) =
        NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
}