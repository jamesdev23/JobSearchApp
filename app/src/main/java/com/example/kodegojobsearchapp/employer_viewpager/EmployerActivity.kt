package com.example.kodegojobsearchapp.employer_viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityEmployerBinding

class EmployerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: Implement Fragments
    }
}