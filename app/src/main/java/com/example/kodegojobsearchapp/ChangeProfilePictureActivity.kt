package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfilePictureBinding

// TODO: profile picture function via Picasso and Intent
class ChangeProfilePictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfilePictureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Change Profile Picture"

        binding.btnCancel.setOnClickListener{
            onBackPressed()
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
}