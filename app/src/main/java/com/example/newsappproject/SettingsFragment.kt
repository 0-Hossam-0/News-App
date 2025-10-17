package com.example.newsappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsappproject.databinding.FragmentSettingsBinding


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

        return binding.root
    }

}