package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.adapter.JobListingDataAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.dao.JobListingDAO
import com.example.kodegojobsearchapp.dao.KodegoJobSearchApplication
import com.example.kodegojobsearchapp.databinding.FragmentJobListingBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class JobListingFragment : Fragment() {

    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobListingDataAdapter: JobListingDataAdapter
    private var jobListingDatas: ArrayList<JobListingData> = arrayListOf()
    private var currentPage = defaultPage
    private var currentQuery = defaultQuery
    private lateinit var dao: JobListingDAO

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
        dao = (requireActivity().application as KodegoJobSearchApplication).jobListingDatabase.jobListingDAO() //TODO: Continue Implementation

        jobListingDataAdapter = JobListingDataAdapter(requireContext(), jobListingDatas, requireActivity().supportFragmentManager)
        binding.appJobListing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.appJobListing.adapter = jobListingDataAdapter

//        getData(defaultQuery, defaultPage)
        getDataFromDAO()

        binding.searchJobListing.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // do nothing coz api needs whole query anyway
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    currentQuery = query
                    getData(query, currentPage)
                }
                return false
            }

        })

        binding.btnPrevious.setOnClickListener {
            if (currentPage > 1) {
                currentPage -= 1
//                getData(currentQuery, currentPage)
                getDataFromDAO() //TODO: Revert in case of errors
            }
        }

        binding.btnNext.setOnClickListener {
            currentPage += 1
//            getData(currentQuery, currentPage)
            getDataFromDAO() //TODO: Revert in case of errors
        }

    }

    private fun getDataFromDAO(){
        binding.appJobListing.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE
        lifecycleScope.launch {
            val list = dao.getJobLists((currentPage - 1) * 10) as ArrayList<JobListingData>
            if (list.isEmpty()){
                getData(currentQuery, currentPage)
            }else{
                jobListingDataAdapter.setList(list)
                binding.appJobListing.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
            }
        }
    }

    private fun getData(queryText: String, page: Int){
        binding.appJobListing.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE

        val call: Call<JobSearchResultResponse> = JobSearchAPIClient
            .getJobSearchData.getJobListing(queryText, page, numPages)
        call.enqueue(object : Callback<JobSearchResultResponse> {
            override fun onFailure(call: Call<JobSearchResultResponse>, t: Throwable) {
                binding.appJobListing.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
                currentPage--

                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<JobSearchResultResponse>,
                response: Response<JobSearchResultResponse>
            ) {
                binding.appJobListing.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
                if (response.isSuccessful) {
                    val response: JobSearchResultResponse = response.body()!!

                    val dataList = response.dataList
                    if(dataList.isNotEmpty()) {
                        jobListingDataAdapter.setList(dataList)
                        lifecycleScope.launch {
                            for (data in dataList) {
                                dao.insert(data)
                                Log.d("API CALL", "${data.jobTitle} ${data.employerName}")
                            }
                        }
                    }else{
                        currentPage--
                        Toast.makeText(
                            requireContext(),
                            "You've reached the end of results",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    currentPage--
                    when (response.code()){
                        HttpURLConnection.HTTP_BAD_REQUEST -> Log.e("API Call", "Bad Request")
                        HttpURLConnection.HTTP_NOT_FOUND -> Log.e("API Call", "Not Found")
                        else -> Log.e("API Call", response.message())
                    }
                }
            }
        })
    }

    companion object {
        var defaultQuery = "Android developer in Metro Manila, Philippines"
        val defaultPage = 1
        val numPages = 1
    }

}