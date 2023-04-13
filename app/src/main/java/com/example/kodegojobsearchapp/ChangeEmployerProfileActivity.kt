package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityChangeEmployerProfileBinding

class ChangeEmployerProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmployerProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmployerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}