package com.example.kodegojobsearchapp.applicant_viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.SignInActivity
import com.example.kodegojobsearchapp.adapter.ViewPagerAdapter
import com.example.kodegojobsearchapp.databinding.ActivityApplicantViewPagerBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class ApplicantViewPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicantViewPagerBinding
    private lateinit var auth: FirebaseAuth
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply{
            title = ""
            setHomeAsUpIndicator(R.mipmap.job_search_icon)
            setDisplayHomeAsUpEnabled(true)
            displayOptions
        }

        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        // uses new adapter to add icons

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, this)
        
        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(JobListingFragment())
        viewPagerAdapter.addFragment(ProfileFragment())
        viewPagerAdapter.addFragment(AccountFragment())


        with(binding.viewPager2) {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = viewPagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            val tabView = viewPagerAdapter.getTabView(position, binding.viewPager2)
            tab.customView = tabView
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this).addOnSuccessListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if(binding.viewPager2.currentItem == 0) {
            super.onBackPressed()
        }else {
            binding.viewPager2.currentItem = binding.viewPager2.currentItem - 1
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        const val ANONYMOUS = "anonymous"
    }
}