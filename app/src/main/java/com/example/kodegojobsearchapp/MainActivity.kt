package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivityMainBinding
import com.example.kodegojobsearchapp.firebase.FirebaseUserDAOImpl
import com.example.kodegojobsearchapp.model.User
import com.example.kodegojobsearchapp.model.UserType
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

}