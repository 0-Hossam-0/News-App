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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentSignUpBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import android.os.Handler
import android.os.Looper
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private val nameEt get() = binding.nameEt
    private val emailEt get() = binding.emailEt
    private val passwordEt get() = binding.passwordEt
    private val repeatPasswordEt get() = binding.repeatPasswordEt
    private val nameErrTv get() = binding.nameErrTv
    private val emailErrTv get() = binding.emailErrTv
    private val passwordErrTv get() = binding.passwordErrTv
    private val successTv get() = binding.successTv
    private val signUpBtn get() = binding.signUpBtn
    private val loadingPb get() = binding.loadingPb
    private val repeatPasswordErrTv get() = binding.repeatPasswordErrTv

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }
    private fun handleSignUp() {
        val inputFields = listOf(
            Pair(nameEt, nameErrTv),
            Pair(emailEt, emailErrTv),
            Pair(passwordEt, passwordErrTv),
            Pair(repeatPasswordEt, repeatPasswordErrTv)
        )
        val inputNames = listOf(
            "name",
            "email",
            "password",
            "repeat_password"
        )

        var isValid = true
        for (index in 0..inputNames.size - 1){
            isValid = validateSingleInput(inputFields[index].first,inputFields[index].second, inputNames[index]) && isValid
        }
        if (!isValid) return
        toggleLoadingStateUI(true)

        val emailStr = emailEt.text.toString().trim()
        val passwordStr = passwordEt.text.toString().trim()
        auth.createUserWithEmailAndPassword(emailStr, passwordStr)
            .addOnCompleteListener(requireActivity()) { task ->
                val successTextView = successTv
                toggleLoadingStateUI(false)
                when {
                    task.isSuccessful -> {
                        successTextView.setText("Account created successfully!")
                        Handler(Looper.getMainLooper()).postDelayed({
                            findNavController().navigate(R.id.LoginFragment)
                        }, 2000)
                        findNavController().navigate(R.id.LoginFragment)
                    }
                    task.exception is com.google.firebase.auth.FirebaseAuthUserCollisionException -> {
                        setErrorUI(Pair(emailEt, emailErrTv), "Email already exists. Please log in instead")
                    }
                    else -> {
                        successTextView.setText("Something went wrong")
                        Log.e("SignUp", "Error: ${task.exception?.message}")
                    }
                }
            }
    }

    private fun setupListeners(){
        signUpBtn.setOnClickListener { handleSignUp() }
        nameEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(nameEt, nameErrTv, "name")
        }

        emailEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(emailEt, emailErrTv, "email")
        }

        passwordEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(passwordEt, passwordErrTv, "password")
        }
        repeatPasswordEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateSingleInput(repeatPasswordEt, repeatPasswordErrTv, "repeat_password")
        }

        binding.logInBtn.setOnClickListener {
            it.findNavController().navigate(R.id.LoginFragment)
        }
    }

    private fun setErrorUI(pair :Pair<EditText, TextView>, errorMessage: String){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_error_border)
        pair.second.text = errorMessage
    }
    private fun setSuccessUI(pair :Pair<EditText, TextView>){
        pair.first.background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_success_border)
        pair.second.text = ""
    }

    private fun toggleLoadingStateUI(isLoading: Boolean){
        loadingPb.visibility = if (isLoading) View.VISIBLE else View.GONE
        signUpBtn.isEnabled = !isLoading
    }

    private fun validateSingleInput(editText: EditText, errorText: TextView, type: String) : Boolean {
        val value = editText.text.toString().trim()

        if (value.isEmpty()) {
            setErrorUI(Pair(editText, errorText), "Empty field")
            return false
        }

        var isValid = true
        when (type) {
            "name" -> {
                if (value.length < 5) {
                    setErrorUI(Pair(editText, errorText), "Full name must be at least 5 characters")
                    isValid = false
                } else {
                    setSuccessUI(Pair(editText, errorText))
                }
            }

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
            "repeat_password" -> {
                if(value != passwordEt.text.toString().trim()){
                    setErrorUI(Pair(editText, errorText), "Repeat Password doesn't match password field")
                    isValid = false
                } else{
                    setSuccessUI(Pair(editText, errorText))
                }
            }
        }
        return isValid
    }


}