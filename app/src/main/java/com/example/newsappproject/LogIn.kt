package com.example.newsappproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LogIn : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.logInBtn.setOnClickListener { handleLogIn() }

        binding.signUpBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_logIn_to_signUp)
        }

        binding.forgetPasswordBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_logIn_to_resetPasswordFragment4)
        }
    }

    private fun setErrorUI(pair :Pair<EditText, TextView>, errorMessage: String){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_error_border)
        pair.second.text = errorMessage
    }
    private fun setSuccessUI(pair :Pair<EditText, TextView>){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_success_border)
        pair.second.setText("")
    }

    private fun toggleLoadingStateUI(isLoading: Boolean){
        binding.loadingPb.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.logInBtn.isEnabled = !isLoading
    }

    private fun handleLogIn() {

        toggleLoadingStateUI(true)

        val emailStr = binding.emailEt.text.toString().trim()
        val passwordStr = binding.passwordEt.text.toString().trim()
        auth.signInWithEmailAndPassword(emailStr, passwordStr)
            .addOnCompleteListener(requireActivity()) { task ->
                val successTextView = binding.successTv
                toggleLoadingStateUI(false)
                when {
                    task.isSuccessful -> {
                        findNavController().navigate(R.id.action_logIn_to_homeFragment)
                    }
                    task.exception is com.google.firebase.auth.FirebaseAuthInvalidUserException -> {
                        setErrorUI(Pair(binding.emailEt, binding.emailErrTv), "Invalid")
                    }
                    else -> {
                        successTextView.setText("Something went wrong")
                        Log.e("SignUp", "Error: ${task.exception?.message}")
                    }
                }
            }
    }
}