package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.databinding.FragmentJobListingBinding
import com.example.kodegojobsearchapp.model.JobListing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: job listing implementation. might need API first

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

//        getData()

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

//    private fun getData(){
//        val apiKey = "b160f8de0fmsh9bf806435608629p1f65b0jsnea1fc1fbc488"
//        val apiHost = "jsearch.p.rapidapi.com"
//        val query = "Python%20developer%20in%20Texas%2C%20USA"
//        val page = 1
//        val numPages = 1
//
//
//        val call: Call<JobSearchResultResponse> = JobSearchAPIClient.getJobSearchData.getJobData(
//            apiKey = apiKey,
//            apiHost = apiHost,
//            query = query,
//            page = page,
//            numPages = numPages
//        )
//
//        call.enqueue(object : Callback<JobSearchResultResponse> {
//            override fun onFailure(call: Call<JobSearchResultResponse>, t: Throwable) {
//                Log.d("API CALL", "Failed API CALL")
//            }
//
//            override fun onResponse(
//                call: Call<JobSearchResultResponse>,
//                response: Response<JobSearchResultResponse>
//            ) {
//                var response: JobSearchResultResponse = response!!.body()!!
//
//                jobListingAdapter!!.setJobListing(response.dataList)
//
//                var jobListings = response.dataList
//                for(jobListing in jobListings) {
//                    Log.d("API CALL", "${jobListing.job_title} ${jobListing.employer_name}")
//                }
//            }
//        })
//    }

}