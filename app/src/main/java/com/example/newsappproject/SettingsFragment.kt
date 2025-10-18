package com.example.newsappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {

    lateinit var binding : FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding.logoutBtn.setOnClickListener {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        findNavController().navigate(R.id.action_settingsFragment_to_LoginFragment)

    }
        return binding.root
    }

}