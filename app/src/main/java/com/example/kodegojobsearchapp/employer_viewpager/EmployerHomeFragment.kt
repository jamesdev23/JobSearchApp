package com.example.kodegojobsearchapp.employer_viewpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.ApplicantSearchActivity
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingAdapter
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.databinding.FragmentEmployerHomeBinding
import com.example.kodegojobsearchapp.model.JobListing

class EmployerHomeFragment : Fragment() {
    private lateinit var _binding: FragmentEmployerHomeBinding
    private val binding get() = _binding
    private lateinit var jobListingAdapter: JobListingAdapter
    private var jobListing: ArrayList<JobListing> = ArrayList()

    init {
        if(this.arguments == null) {
            getTabInfo()
        }
    }
    private fun getTabInfo(){
        this.arguments = Bundle().apply {
            putString(FragmentKeys.TabName, "Home")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmployerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        jobListingAdapter = JobListingAdapter(jobListing)
        binding.listJobList.layoutManager = LinearLayoutManager(activity)
        binding.listJobList.adapter = jobListingAdapter

        binding.btnApplicantSearch.setOnClickListener {
            val intent = Intent(requireContext(), ApplicantSearchActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateJobListing.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
            viewPager?.currentItem = 1
        }
    }

    fun init(){
        jobListing.add(JobListing("Software Developer", "New York, NY", "Looking for an experienced software developer to join our team"))
        jobListing.add(JobListing("Data Analyst", "San Francisco, CA", "Seeking a data analyst with experience in machine learning"))
        jobListing.add(JobListing("Product Manager", "Los Angeles, CA", "Exciting opportunity for a product manager with a background in mobile apps"))
        jobListing.add(JobListing("Marketing Coordinator", "Chicago, IL", "Join our growing marketing team as a coordinator"))
        jobListing.add(JobListing("Graphic Designer", "Houston, TX", "In-house graphic designer needed for a variety of projects"))
    }
}