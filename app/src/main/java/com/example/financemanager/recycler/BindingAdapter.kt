package com.example.financemanager.recycler

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("expenseText")
fun setExpenseText(view: TextView, expense: String?) {
    view.text = expense ?: ""
}

@BindingAdapter("amountText")
fun setAmountText(view: TextView, amount: Double?) {
    view.text = amount?.toString() ?: "0.00"
} 