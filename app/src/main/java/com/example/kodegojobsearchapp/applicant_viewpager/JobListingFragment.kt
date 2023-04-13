package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.adapter.JobListingDataAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.databinding.FragmentJobListingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: job listing implementation. might need API first. also, add searchbar (in progress)

class JobListingFragment : Fragment() {

    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobListingDataAdapter: JobListingDataAdapter
    private var jobListingDatas: ArrayList<JobListingData> = arrayListOf()

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

//        binding.appSearchJobListing.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // TODO: add dao search function implementation. also, update list
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // TODO: add filter
//                return false
//            }
//
//        })

        jobListingDataAdapter = JobListingDataAdapter(requireContext(), jobListingDatas, requireActivity().supportFragmentManager)
        binding.appJobListing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.appJobListing.adapter = jobListingDataAdapter
        getData(defaultQuery)


    }


    override fun onDestroy() {
        super.onDestroy()

    }


    private fun getData(queryText: String){
        val call: Call<JobSearchResultResponse> = JobSearchAPIClient
            .getJobSearchData.getJobData(queryText, page, numPages)

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

                val dataLists = response.dataList
                for(data in dataLists) {
                    Log.d("API CALL", "${data.jobTitle} ${data.employerName}")
                }
            }
        })
    }

    companion object {
        var defaultQuery = "Android developer in Texas, USA"
        val page = 1
        val numPages = 1
    }

}