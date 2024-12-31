package com.example.financemanager.LoginAndSignUp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.financemanager.MainActivity
import com.example.financemanager.MainActivity.Companion.auth
import com.example.financemanager.R
import com.example.financemanager.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailLoginEt.text.toString().trim()
            val password = binding.passwordLoginEt.text.toString().trim()
            if (email.isNotEmpty()&&password.isNotEmpty()){
                Login(email,password)

            }else {
                activity.let {
                    Toast.makeText(it,"Please Enter Id and Password",Toast.LENGTH_LONG).show()
                }

            }

        }
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root

    }

    private fun Login(email:String,password:String) {


        MainActivity.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_dashBoard)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    activity.let { Toast.makeText(
                        it,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show() }

                }
            }

    }


}