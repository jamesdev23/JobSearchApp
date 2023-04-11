package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfileDetailsBinding

//TODO: implement update profile details using DAO

class ChangeProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Change Profile Details"

        binding.btnCancal.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}