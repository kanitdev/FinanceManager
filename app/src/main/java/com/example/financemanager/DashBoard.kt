package com.example.financemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financemanager.recycler.Adapter
import com.example.financemanager.recycler.Expense
import com.example.financemanager.databinding.FragmentDashBoardBinding


class DashBoard : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)


        // Create test data
        val expenseList = arrayListOf(
            Expense("Salary", 50000.0),
            Expense("Rent", -15000.0),
            Expense("Groceries", -2500.0),
            Expense("Freelance", 10000.0),
            Expense("Internet Bill", -1499.0),
            Expense("Investment Returns", 5000.0),
            Expense("Restaurant", -2000.0),
            Expense("Mobile Bill", -999.0),
            Expense("Side Project", 15000.0),
            Expense("Shopping", -3500.0)
        )

        updateDashboard(expenseList)

        // Set up RecyclerView
        val adapter = Adapter(expenseList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_addTransaction)
        }

        return binding.root
    }
    private fun updateDashboard(expenseList:ArrayList<Expense>){
        var totalAmount = 0.0
        var totalIncome = 0.0
        var totalExpense = 0.0


        expenseList.forEach { expense ->
            expense.amt?.let { amount ->
                totalAmount += amount
                if (amount > 0) {
                    totalIncome += amount
                } else {
                    totalExpense += kotlin.math.abs(amount)
                }
            }
        }

        binding.totalAmtTv.text = "₹%.2f".format(totalAmount)
        binding.incomeTv.text = "₹%.2f".format(totalIncome)
        binding.textView5.text = "₹%.2f".format(totalExpense)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}