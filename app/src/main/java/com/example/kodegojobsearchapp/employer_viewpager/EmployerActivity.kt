package com.example.kodegojobsearchapp.employer_viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.MyBottomSheetDialogFragment
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.SignInActivity
import com.example.kodegojobsearchapp.adapter.FragmentAdapter
import com.example.kodegojobsearchapp.adapter.ViewPagerAdapter
import com.example.kodegojobsearchapp.applicant_viewpager.*
import com.example.kodegojobsearchapp.databinding.ActivityEmployerBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmployerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployerBinding
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerBinding.inflate(layoutInflater)
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

        // uses new adapter

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, this)

        viewPagerAdapter.addFragment(EmployerHomeFragment())
        viewPagerAdapter.addFragment(EmployerJobsFragment())
        viewPagerAdapter.addFragment(EmployerProfileFragment())
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
//                val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
//                bottomSheetDialogFragment.show(supportFragmentManager, "MyBottomSheetDialogFragment")
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

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    companion object {
        private const val TAG = "MainActivity"
        const val ANONYMOUS = "anonymous"
    }

    override fun onBackPressed() {
        if(binding.viewPager2.currentItem == 0) {
            super.onBackPressed()
        }else {
            binding.viewPager2.currentItem = binding.viewPager2.currentItem - 1
        }
    }
}