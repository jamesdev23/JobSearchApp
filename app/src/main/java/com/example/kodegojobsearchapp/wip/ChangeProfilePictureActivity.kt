package com.example.kodegojobsearchapp.wip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfilePictureBinding

class ChangeProfilePictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfilePictureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener{
            onBackPressed()
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
}