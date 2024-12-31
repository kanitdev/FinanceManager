package com.example.financemanager.recycler

import android.view.LayoutInflater
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.databinding.ExpenseBinding

class Adapter(private val expenses: ArrayList<Expense>) :
    RecyclerView.Adapter<Adapter.TransactionHolder>() {

    class TransactionHolder(private val itemBinding: ExpenseBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currentExpense: Expense) {
            itemBinding.list = currentExpense

            currentExpense.amt?.let { amount ->
                when{
                    amount > 0->{
                        itemBinding.amt.setTextColor(Color.GREEN)
                        itemBinding.amt.text = "+₹%.2f".format(amount)
                    }
                    amount < 0 -> {
                        itemBinding.amt.setTextColor(Color.RED)
                        itemBinding.amt.text = "-₹%.2f".format(kotlin.math.abs(amount))
                    }
                    else -> {
                        itemBinding.amt.setTextColor(Color.BLACK)
                        itemBinding.amt.text = "₹%.2f".format(amount)
                    }
                }

            }
                ?: run {
                    itemBinding.amt.text = "₹0.00"
                    itemBinding.amt.setTextColor(Color.BLACK)
                }

            itemBinding.executePendingBindings()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val binding = ExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHolder(binding)

    }

    override fun getItemCount(): Int = expenses.size

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val currentExpense = expenses[position]
        holder.bind(currentExpense)
    }


}