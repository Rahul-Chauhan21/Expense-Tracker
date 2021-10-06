package com.example.expensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ItemTransactionLayoutBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.formatAmount

class TransactionAdapter(private val context: Context) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val allTransactions = ArrayList<Transaction>()

    inner class TransactionViewHolder(val binding: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        with(holder) {
            with(allTransactions[position]) {
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
                when (binding.tvCategory.text) {
                    "Housing" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_housing)
                    }
                    "Transportation" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_transport)
                    }
                    "Food" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_food)
                    }
                    "Utilities" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_utilities)
                    }
                    "Insurance" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_insurance)
                    }
                    "Healthcare" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_medical)
                    }
                    "Saving & Debts" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_savings)
                    }
                    "Personal Spending" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_personal_spending)
                    }
                    "Entertainment" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_entertainment)
                    }
                    "Miscellaneous" -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_others)
                    }
                    else -> {
                        binding.ivTransaction.setImageResource(R.drawable.ic_others)
                    }
                }

                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { it(this) }
                }
            }
        }
    }

    override fun getItemCount() = allTransactions.size

    fun updateList(newList: List<Transaction>) {
        allTransactions.clear()
        allTransactions.addAll(newList)

        notifyDataSetChanged()
    }


    // on item click listener
    private var onItemClickListener: ((Transaction) -> Unit)? = null
    fun setOnItemClickListener(listener: (Transaction) -> Unit) {
        onItemClickListener = listener
    }
}