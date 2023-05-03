package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingDataAdapter
import com.example.kodegojobsearchapp.adapter.ListJobApplicationAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.dao.KodegoJobSearchApplication
import com.example.kodegojobsearchapp.dao.JobListingDAO
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.firebase.FirebaseJobApplicationDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.example.kodegojobsearchapp.model.JobApplication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

// TODO: recent job list implementation using firebase
//  Retrieve Applied Jobs
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: JobListingDAO
    private lateinit var jobListingDataAdapter: JobListingDataAdapter
    private var jobListingDatas: ArrayList<JobListingData> = arrayListOf()
    private lateinit var applicationsDAO: FirebaseJobApplicationDAOImpl
    private lateinit var jobApplicationsAdapter: ListJobApplicationAdapter
    private val applications: ArrayList<JobApplication> = ArrayList()

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
        setHasOptionsMenu(true)
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
        dao = (requireActivity().application as KodegoJobSearchApplication).jobListingDatabase.jobListingDAO()
        applicationsDAO = FirebaseJobApplicationDAOImpl(requireContext())



        jobListingDataAdapter = JobListingDataAdapter(requireContext(), jobListingDatas, requireActivity().supportFragmentManager)
        binding.jobListingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.jobListingList.adapter = jobListingDataAdapter

        binding.textFeaturedQuery.text = defaultQuery

//        getData()  // uncomment to check api
        populateList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_refresh -> {
                getData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun populateList(){
        binding.jobListingList.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE
        lifecycleScope.launch {
            val list = dao.getJobLists() as ArrayList<JobListingData>
            if (list.isEmpty()){
                getData()
            }else{
                jobListingDataAdapter.setList(list)
                binding.jobListingList.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
            }
        }
    }

    private fun getData(){
        val call: Call<JobSearchResultResponse> = JobSearchAPIClient
            .getJobSearchData.getJobListing(defaultQuery, page, numPages)
        binding.jobListingList.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE

        call.enqueue(object : Callback<JobSearchResultResponse> {
            override fun onFailure(call: Call<JobSearchResultResponse>, t: Throwable) {
                binding.jobListingList.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE

                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<JobSearchResultResponse>,
                response: Response<JobSearchResultResponse>
            ) {
                binding.jobListingList.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE
                if (response.isSuccessful) {
                    val response: JobSearchResultResponse =
                        response.body()!!
                    val dataList = response.dataList
                    jobListingDataAdapter.setList(dataList)

                    lifecycleScope.launch {
                        dao.clean()
                        for (data in dataList) {
                            dao.insert(data)
                            Log.d("API CALL", "${data.jobTitle} ${data.employerName}")
                        }
                    }
                }else{
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
        val page = 1
        val numPages = 1
    }
}