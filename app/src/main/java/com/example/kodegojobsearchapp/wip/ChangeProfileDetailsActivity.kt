package com.example.kodegojobsearchapp.wip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfileDetailsBinding

//TODO: change activities in 'wip' folder to fragment and add to button OnClickListener

class ChangeProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancal.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}