package com.example.financemanager.LoginAndSignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.financemanager.MainActivity
import com.example.financemanager.R

import com.example.financemanager.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)

        binding.signInBtn.setOnClickListener {
           findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        binding.signUpBtn.setOnClickListener{
            val email = binding.emailSignInEt.text.toString().trim()
            val password=binding.passwordSignInEt.text.toString().trim()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                SignIn(email,password)
                }else{
                    activity.let {
                        Toast.makeText(it,"Please fill all fields",Toast.LENGTH_SHORT).show()
                    }
            }
            }
        return binding.root
        }


    private fun SignIn(email:String,password:String) {
        MainActivity.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    Toast.makeText(requireContext(),"User Registered",Toast.LENGTH_LONG).show()

            }
        }.addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }


    }




