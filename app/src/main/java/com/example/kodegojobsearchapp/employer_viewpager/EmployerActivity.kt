package com.example.kodegojobsearchapp.employer_viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.adapter.FragmentAdapter
import com.example.kodegojobsearchapp.applicant_viewpager.AccountFragment
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.databinding.ActivityEmployerBinding
import com.google.android.material.tabs.TabLayoutMediator

class EmployerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployerBinding
    private lateinit var fragmentAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO:
        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(EmployerHomeFragment())
        fragmentAdapter.addFragment(EmployerJobsFragment())
        fragmentAdapter.addFragment(EmployerProfileFragment())
        fragmentAdapter.addFragment(AccountFragment())

        with(binding.viewPager2) {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = fragmentAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            var text: String = "Unknown"
            with(fragmentAdapter.fragmentList[position]) {
                arguments?.let {
                    text = it.getString(FragmentKeys.TabName) ?: "Unknown"
                }
            }
            tab.text = text
        }.attach()
    }
}