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

        binding.emailEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(binding.emailEt, binding.emailErrTv, "email")
        }

        binding.passwordEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(binding.passwordEt, binding.passwordErrTv, "password")
        }

        binding.signUpBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_logIn_to_signUp)
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

    private fun validateSingleInput(
        editText: EditText,
        errorText: TextView,
        type: String
    ): Boolean {
        val value = editText.text.toString().trim()

        if (value.isEmpty()) {
            setErrorUI(Pair(editText, errorText), "Empty field")
            return false
        }

        var isValid = true
        when(type){
            "email" -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                    isValid = false
                    setErrorUI(Pair(editText, errorText), "Invalid email address")
                } else {
                    setSuccessUI(Pair(editText, errorText))
                }
            }

            "password" -> {
                if (value.length < 6) {
                    setErrorUI(Pair(editText, errorText), "Password must be at least 6 characters")
                    isValid = false
                } else {
                    setSuccessUI(Pair(editText, errorText))
                }
            }
        }
        return isValid
    }

    private fun handleLogIn() {
        val inputFields = listOf(
            Pair(binding.emailEt, binding.emailErrTv),
            Pair(binding.passwordEt, binding.passwordErrTv),
        )
        val inputNames = listOf(
            "email",
            "password"
        )

        var isValid = true
        for (index in 0 until inputNames.size){
            isValid = validateSingleInput(inputFields[index].first,inputFields[index].second, inputNames[index]) && isValid
        }
        if (!isValid) return
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
                    task.exception is com.google.firebase.auth.FirebaseAuthUserCollisionException -> {
                        setErrorUI(inputFields.first(), "Email already exists. Please log in instead")
                    }
                    else -> {
                        successTextView.setText("Something went wrong")
                        Log.e("SignUp", "Error: ${task.exception?.message}")
                    }
                }
            }
    }
}