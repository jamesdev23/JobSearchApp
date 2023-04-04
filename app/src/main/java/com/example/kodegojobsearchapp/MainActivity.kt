package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivityMainBinding
import com.example.kodegojobsearchapp.employer_viewpager.EmployerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        binding.btnJobseeker.setOnClickListener {
            val intent = Intent(this, ApplicantViewPagerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data","from_welcome_page")
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

        binding.btnEmployer.setOnClickListener {
            val intent = Intent(this, EmployerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data","from_welcome_page")
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
                bottomSheetDialogFragment.show(supportFragmentManager, "MyBottomSheetDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}