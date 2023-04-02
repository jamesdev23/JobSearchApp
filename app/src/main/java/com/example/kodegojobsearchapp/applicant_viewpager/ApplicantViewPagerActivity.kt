package com.example.kodegojobsearchapp.applicant_viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.MyBottomSheetDialogFragment
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.FragmentAdapter
import com.example.kodegojobsearchapp.adapter.ViewPagerAdapter
import com.example.kodegojobsearchapp.databinding.ActivityApplicantViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ApplicantViewPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicantViewPagerBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Job Search"
        supportActionBar?.displayOptions

//        val fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
//
//        fragmentAdapter.addFragment(HomeFragment())
//        fragmentAdapter.addFragment(JobListingFragment())
//        fragmentAdapter.addFragment(ProfileFragment())
//        fragmentAdapter.addFragment(AccountFragment())
//
//        with(binding.viewPager2) {
//            orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            adapter = fragmentAdapter
//        }
//
//        val tabs: ArrayList<String> = ArrayList()
//        tabs.add("Home")
//        tabs.add("Job Listing")
//        tabs.add("Profile")
//        /**
//         * Used Fragment Arguments to get Fragment TabName
//         */
//        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
//            var text: String = "Unknown"
//            with(fragmentAdapter.fragmentList[position]) {
//                arguments?.let {
//                    text = it.getString(FragmentKeys.TabName) ?: "Unknown"
//                }
//            }
//            tab.text = text
//        }.attach()

        // uses new adapter to add icon

        val fragmentList: ArrayList<Fragment> = ArrayList()

        fragmentList.add(HomeFragment())
        fragmentList.add(JobListingFragment())
        fragmentList.add(ProfileFragment())
        fragmentList.add(AccountFragment())

        val viewPagerAdapter = ViewPagerAdapter(fragmentList,this, this)
        binding.viewPager2.adapter = viewPagerAdapter

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
                val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
                bottomSheetDialogFragment.show(supportFragmentManager, "MyBottomSheetDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if(binding.viewPager2.currentItem == 0) {
            super.onBackPressed()
        }else {
            binding.viewPager2.currentItem = binding.viewPager2.currentItem - 1
        }
    }


}