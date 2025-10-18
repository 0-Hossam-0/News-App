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
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentLoginBinding
    private var isInputsValid: MutableMap<String, Boolean> = mutableMapOf(
        "email" to false,
        "password" to false
    )

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
            it.findNavController().navigate(R.id.action_LoginFragment_to_signUpFragment)
        }
        binding.emailEt.addTextChangedListener {
            validateSingleInput(binding.emailEt, binding.emailErrTv,"email")
            val allValid = isInputsValid["email"] == true && isInputsValid["password"] == true
            binding.logInBtn.isEnabled = allValid


        }
        binding.passwordEt.addTextChangedListener {
            validateSingleInput(binding.passwordEt, binding.passwordErrTv,"password")

            val allValid = isInputsValid["email"] == true && isInputsValid["password"] == true
            binding.logInBtn.isEnabled = allValid
        }

    }

    private fun setErrorUI(pair :Pair<EditText, TextView>){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_error_border)
        pair.second.text = "Empty Field"
    }
    private fun setDefaultUI(pair :Pair<EditText, TextView>) {
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_button_default_border)
        pair.second.text = ""
    }
    private fun setSuccessUI(pair :Pair<EditText, TextView>){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_success_border)
        pair.second.text = ""
    }
    private fun toggleLoadingStateUI(isLoading: Boolean){
        binding.loadingPb.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.logInBtn.isEnabled = !isLoading
    }

    private fun validateSingleInput(editText: EditText, errorText: TextView, type: String) {
        val value = editText.text.toString().trim()

        if (value.isEmpty()) {
            setErrorUI(Pair(editText, errorText))
            isInputsValid[type] = false
            return
        }

        when (type) {
            "email" -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                    isInputsValid["email"] = false
                    setDefaultUI(Pair(editText, errorText))
                } else{
                    isInputsValid["email"] = true
                    setSuccessUI(Pair(editText, errorText))

                }
            }

            "password" -> {
                if (value.length < 6) {
                    isInputsValid["password"] = false
                    setDefaultUI(Pair(editText, errorText))
                }
                else{
                    isInputsValid["password"] = true
                    setSuccessUI(Pair(editText, errorText))
                }
            }
        }
    }
    private fun handleLogIn() {

        toggleLoadingStateUI(true)

        val emailStr = binding.emailEt.text.toString().trim()
        val passwordStr = binding.passwordEt.text.toString().trim()
        auth.signInWithEmailAndPassword(emailStr, passwordStr)
            .addOnCompleteListener(requireActivity()) { task ->
                val errorTextView = binding.errorTv
                toggleLoadingStateUI(false)

                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_logIn_to_homeFragment)
                } else {
                    val exception = task.exception
                    when (exception) {
                        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException, is com.google.firebase.auth.FirebaseAuthInvalidUserException -> {
                            errorTextView?.text = "Invalid email or password"
                        }
                        else -> {
                            errorTextView?.text = "Something went wrong: ${exception?.message}"
                        }
                    }
                    Log.e("LogIn", "Error: ${exception?.message}")
                }
            }

    }
}

