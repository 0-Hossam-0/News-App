package com.example.newsappproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentSettingsBinding
import com.example.newsappproject.util.CountryCodes
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val selectedCountries = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedCountries = prefs.getStringSet("selected_countries", emptySet())?.toMutableSet()
        if (!savedCountries.isNullOrEmpty()) {
            selectedCountries.addAll(savedCountries)
            updateCountryList()
        }
        binding.backArrow.setOnClickListener { findNavController().popBackStack() }
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_settingsFragment_to_LoginFragment)
        }

        val countryList = CountryCodes.countries.map { "${it.value} (${it.key.uppercase()})" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, countryList)
        binding.autoCompleteCountry.setAdapter(adapter)
        binding.autoCompleteCountry.setOnClickListener {
            binding.autoCompleteCountry.showDropDown()
        }

        binding.autoCompleteCountry.setOnItemClickListener { _, _, position, _ ->
            val selected = countryList[position]
            if (!selectedCountries.contains(selected)) {
                if (selectedCountries.size < 3) {
                    selectedCountries.add(selected)
                } else {
                    binding.autoCompleteCountry.error = "You can select up to 3 countries only"
                }
            }
            updateCountryList()
            prefs.edit().putStringSet("selected_countries", selectedCountries.toSet()).apply()
            binding.autoCompleteCountry.text.clear()
        }
    }

    private fun updateCountryList() {
        val container = binding.selectedCountriesContainer
        container.removeAllViews()

        selectedCountries.forEach { country ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 10, 0, 10)
                layoutParams = params
            }

            val countryText = TextView(requireContext()).apply {
                text = country
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.black))
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                layoutParams = params
            }

            val removeBtn = Button(requireContext()).apply {
                text = "Remove"
                setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                setTextColor(resources.getColor(android.R.color.white))
                setOnClickListener {
                    selectedCountries.remove(country)
                    updateCountryList()
                    requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putStringSet("selected_countries", selectedCountries.toSet())
                        .apply()
                }
            }

            row.addView(countryText)
            row.addView(removeBtn)
            container.addView(row)
        }
    }
}
