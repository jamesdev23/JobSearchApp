package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingAdapter
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.databinding.FragmentJobListingBinding
import com.example.kodegojobsearchapp.model.JobListing

class JobListingFragment : Fragment() {

    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobListingAdapter: JobListingAdapter
    private val jobListing: ArrayList<JobListing> = ArrayList()

    init {
        if(this.arguments == null) {
            getTabInfo()
        }
    }
    private fun getTabInfo(){
        this.arguments = Bundle().apply {
            putString(FragmentKeys.TabName, "Job Listing")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        jobListingAdapter = JobListingAdapter(jobListing)
        binding.appJobList.layoutManager = LinearLayoutManager(activity)
        binding.appJobList.adapter = jobListingAdapter
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun init(){
        jobListing.add(JobListing("Software Developer", "New York, NY", "Looking for an experienced software developer to join our team"))
        jobListing.add(JobListing("Data Analyst", "San Francisco, CA", "Seeking a data analyst with experience in machine learning"))
        jobListing.add(JobListing("Product Manager", "Los Angeles, CA", "Exciting opportunity for a product manager with a background in mobile apps"))
        jobListing.add(JobListing("Marketing Coordinator", "Chicago, IL", "Join our growing marketing team as a coordinator"))
        jobListing.add(JobListing("Graphic Designer", "Houston, TX", "In-house graphic designer needed for a variety of projects"))
    }
}