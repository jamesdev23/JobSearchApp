package com.example.kodegojobsearchapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashDisplayLength = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler().postDelayed({
            val mainIntent = Intent(this, ApplicantViewPagerActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, splashDisplayLength.toLong())
    }
}