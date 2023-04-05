package com.example.kodegojobsearchapp.wip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityJobSeekerSearchBinding

class JobSeekerSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobSeekerSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSeekerSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Search Applicant"
    }
}