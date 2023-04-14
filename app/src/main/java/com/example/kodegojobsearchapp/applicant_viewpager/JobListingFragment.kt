package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingDataAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.databinding.FragmentJobListingBinding
import com.example.kodegojobsearchapp.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: job listing implementation. might need API first. also, add searchbar (in progress)

class JobListingFragment : Fragment() {

    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobListingDataAdapter: JobListingDataAdapter
    private var jobListingDatas: ArrayList<JobListingData> = arrayListOf()
    private lateinit var progressDialog: ProgressDialog

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
        progressDialog = ProgressDialog(binding.root.context)
        jobListingDataAdapter = JobListingDataAdapter(requireContext(), jobListingDatas, requireActivity().supportFragmentManager)
        binding.appJobListing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.appJobListing.adapter = jobListingDataAdapter

        getData(defaultQuery)

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


    }

    private fun getData(queryText: String){
        binding.appJobListing.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE

        val call: Call<JobSearchResultResponse> = JobSearchAPIClient
            .getJobSearchData.getJobListing(queryText, page, numPages)
        call.enqueue(object : Callback<JobSearchResultResponse> {
            override fun onFailure(call: Call<JobSearchResultResponse>, t: Throwable) {
                binding.appJobListing.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE

                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<JobSearchResultResponse>,
                response: Response<JobSearchResultResponse>
            ) {
                var response: JobSearchResultResponse = response.body()!!

                jobListingDataAdapter.setList(response.dataList)

                val dataLists = response.dataList
                for(data in dataLists) {
                    Log.d("API CALL", "${data.jobTitle} ${data.employerName}")
                }

                binding.appJobListing.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
            }
        })
    }

    companion object {
        var defaultQuery = "Android developer in Texas, USA"
        val page = 1
        val numPages = 1
    }

}