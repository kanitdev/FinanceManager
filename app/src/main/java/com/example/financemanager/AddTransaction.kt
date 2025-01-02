package com.example.financemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.financemanager.databinding.FragmentAddTransactionBinding
import com.example.financemanager.room.AppDatabase
import com.example.financemanager.room.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddTransaction : Fragment() {

    private var _binding:FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    lateinit var db:AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater,container,false)

        db=AppDatabase.getDatabase(requireContext())


        binding.addTransactionBtn.setOnClickListener {
            val label = binding.labelInput.text.toString().trim()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()
            val desrciption = binding.descInput.text.toString().trim()

            if (label.isEmpty()){
                binding.labelLayout.error = "Please enter a valid label"
            }
            else if (amount == null) {
                binding.amountLayout.error = "Please enter a amount"
            }
            else{
                val transaction = Expense(null,label,amount,desrciption)
                insert(transaction)
            }




        }

        binding.labelInput.addTextChangedListener {
            if(it!!.count()>0){
                binding.labelLayout.error = null

            }
        }
        binding.amountInput.addTextChangedListener {
            if(it!!.count()>0){
                binding.amountLayout.error = null

            }
        }



        binding.closeBtn.setOnClickListener{
            findNavController().navigate(R.id.action_addTransaction_to_dashBoard)
        }




        return binding.root
    }

    private fun insert(expense: Expense){
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                db.transactionDao().insert(expense)
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_addTransaction_to_dashBoard)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to add transaction", Toast.LENGTH_SHORT).show()
                }

        }





    }


}
}