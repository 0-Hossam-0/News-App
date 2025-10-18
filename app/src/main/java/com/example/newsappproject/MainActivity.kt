package com.example.newsappproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.newsappproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
    }
    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.main)
        val currentUser = auth.currentUser

        if (currentUser == null) {
            navController.navigate(R.id.LoginFragment)
        } else {
            navController.navigate(R.id.HomeFragment)
        }
    }
}
