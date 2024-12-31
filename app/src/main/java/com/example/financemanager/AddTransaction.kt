package com.example.financemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.financemanager.databinding.FragmentAddTransactionBinding


class AddTransaction : Fragment() {

    private var _binding:FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater,container,false)

        val label = binding.labelInput.text.toString()
        val amount = binding.amountInput.text.toString().toDoubleOrNull()

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


        binding.addTransactionBtn.setOnClickListener {
            if (label.isEmpty())
                binding.labelLayout.error = "Please enter a valid label"
            if (amount == null) {
                binding.amountLayout.error = "Please enter a amount"


            }
        }




        return binding.root
    }


}