package com.example.kodegojobsearchapp.employer_viewpager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.kodegojobsearchapp.ApplicantSearchActivity
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.adapter.JobListingAdapter
import com.example.kodegojobsearchapp.adapter.UserListAdapter
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api.UserListAPIClient
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.UserListResponse
import com.example.kodegojobsearchapp.api_model.UserListData
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.applicant_viewpager.HomeFragment
import com.example.kodegojobsearchapp.databinding.FragmentEmployerHomeBinding
import com.example.kodegojobsearchapp.model.JobListing
import com.example.kodegojobsearchapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* TODO: (anyone) employer home uses firebase adapter to show it's own job listing
* */

class EmployerHomeFragment : Fragment() {
    private lateinit var _binding: FragmentEmployerHomeBinding
    private val binding get() = _binding

    private lateinit var userListAdapter: UserListAdapter
    private var userList: ArrayList<UserListData> = arrayListOf()
    private var startIndex = 1

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

        userListAdapter = UserListAdapter(requireContext(), userList, requireActivity().supportFragmentManager)
        binding.applicantList.layoutManager = LinearLayoutManager(activity)
        binding.applicantList.adapter = userListAdapter
        
        getData()

        binding.btnApplicantSearch.setOnClickListener {
            val intent = Intent(requireContext(), ApplicantSearchActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateJobListing.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
            viewPager?.currentItem = 1
        }
    }

    private fun getData(){
        val call: Call<UserListResponse> = UserListAPIClient.getUserListData.getUserList(startIndex)

        call.enqueue(object : Callback<UserListResponse> {
            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                var response: UserListResponse = response!!.body()!!

                userListAdapter.setList(response.dataList)

                val dataLists = response.dataList
                for(data in dataLists) {
                    Log.d("API CALL", "${data.firstName} ${data.lastName}")
                }
            }
        })
    }

}