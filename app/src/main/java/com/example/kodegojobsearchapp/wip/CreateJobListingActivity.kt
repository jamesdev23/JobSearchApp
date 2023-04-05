package com.example.kodegojobsearchapp.wip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityCreateJobListingBinding

class CreateJobListingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateJobListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateJobListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Create Job Listing"
    }
}