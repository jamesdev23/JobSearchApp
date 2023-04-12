package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingAdapter
import com.example.kodegojobsearchapp.adapter.JobListingDataAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.model.JobListing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

// TODO: (anyone) applicant home implementation. might need to wait for API
// TODO: additional: fix failed api call issue

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobListingDataAdapter: JobListingDataAdapter
    private var jobListingDatas: ArrayList<JobListingData> = arrayListOf()
//    private lateinit var jobListingAdapter: JobListingAdapter
//    private var jobListing: ArrayList<JobListing> = arrayListOf()

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        init()

        jobListingDataAdapter = JobListingDataAdapter(requireActivity().applicationContext, jobListingDatas)

//        jobListingAdapter = JobListingAdapter(jobListing)
        binding.jobListingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.jobListingList.adapter = jobListingDataAdapter

//        binding.jobListingList.adapter = jobListingAdapter

        getData()  // uncomment to check api
    }



    override fun onDestroy() {
        super.onDestroy()

    }


//    fun init(){
//        jobListing.add(JobListing("Software Developer", "New York, NY", "Looking for an experienced software developer to join our team"))
//        jobListing.add(JobListing("Data Analyst", "San Francisco, CA", "Seeking a data analyst with experience in machine learning"))
//        jobListing.add(JobListing("Product Manager", "Los Angeles, CA", "Exciting opportunity for a product manager with a background in mobile apps"))
//        jobListing.add(JobListing("Marketing Coordinator", "Chicago, IL", "Join our growing marketing team as a coordinator"))
//        jobListing.add(JobListing("Graphic Designer", "Houston, TX", "In-house graphic designer needed for a variety of projects"))
//    }

    private fun getData(){
        val call: Call<JobSearchResultResponse> = JobSearchAPIClient
            .getJobSearchData.getJobData(query, page, numPages)

        call.enqueue(object : Callback<JobSearchResultResponse> {
            override fun onFailure(call: Call<JobSearchResultResponse>, t: Throwable) {
                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<JobSearchResultResponse>,
                response: Response<JobSearchResultResponse>
            ) {
                var response: JobSearchResultResponse = response!!.body()!!

                jobListingDataAdapter!!.setList(response.dataList)

                val jobListings = response.dataList
                for(jobListing in jobListings) {
                    Log.e("API CALL", "${jobListing.jobTitle} ${jobListing.employerName}")
                }
            }
        })
    }

    companion object {
        val query = "Python%20developer%20in%20Texas%2C%20USA"
        val page = 1
        val numPages = 1
    }

}