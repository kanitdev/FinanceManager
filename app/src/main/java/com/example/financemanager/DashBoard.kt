package com.example.financemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.financemanager.recycler.Adapter
import com.example.financemanager.databinding.FragmentDashBoardBinding
import com.example.financemanager.room.AppDatabase
import com.example.financemanager.room.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashBoard : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        val expenseList = null
        db = AppDatabase.getDatabase(requireContext())


        // Set up RecyclerView
        val adapter = Adapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_addTransaction)
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val transactions = (binding.recyclerView.adapter as Adapter).getExpenses()
                deleteTransaction(transactions[position])
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView)



        return binding.root
    }

    private fun deleteTransaction(expense: Expense){
        lifecycleScope.launch(Dispatchers.IO) {
                db.transactionDao().delete(expense)
                loadTransactions()

        }

    }

    private fun loadTransactions() {
        lifecycleScope.launch(Dispatchers.IO) {
            val transactions = db.transactionDao().getAllOrderedByLatest()
            withContext(Dispatchers.Main) {
               var adapter = Adapter(transactions)
                binding.recyclerView.adapter = adapter
                updateDashboard(transactions)
            }
        }
    }

    private fun updateDashboard(expenseList:List<Expense>){
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

    override fun onResume() {
        super.onResume()
        loadTransactions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}