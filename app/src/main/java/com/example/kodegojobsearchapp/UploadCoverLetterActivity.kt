package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityUploadCoverLetterBinding

class UploadCoverLetterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadCoverLetterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadCoverLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Upload CV"
    }
}