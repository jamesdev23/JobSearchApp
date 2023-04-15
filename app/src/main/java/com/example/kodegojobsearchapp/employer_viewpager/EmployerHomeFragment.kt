package com.example.kodegojobsearchapp.employer_viewpager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.adapter.UserListAdapter
import com.example.kodegojobsearchapp.api.UserListAPIClient
import com.example.kodegojobsearchapp.api_model.UserListData
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.databinding.FragmentEmployerHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* TODO: urgent. add search function
* */

class EmployerHomeFragment : Fragment() {
    private lateinit var _binding: FragmentEmployerHomeBinding
    private val binding get() = _binding

    private lateinit var userListAdapter: UserListAdapter
    private var userList: ArrayList<UserListData> = arrayListOf()
    private var userListSize = 10

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

//        binding.btnApplicantSearch.setOnClickListener {
//            val intent = Intent(requireContext(), ApplicantSearchActivity::class.java)
//            startActivity(intent)
//        }

    }

    private fun getData(){
        val call: Call<List<UserListData>> = UserListAPIClient.getUserListData.getUserList(userListSize, responseType)

        call.enqueue(object : Callback<List<UserListData>> {
            override fun onFailure(call: Call<List<UserListData>>, t: Throwable) {
                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<UserListData>>,
                response: Response<List<UserListData>>
            ) {
                var response: List<UserListData> = response!!.body()!!

                var userList = response as ArrayList

                userListAdapter.setList(userList)

                for(user in userList) {
                    Log.d("API CALL", "${user.firstName} ${user.lastName}")
                }

            }
        })
    }

    companion object{
        const val responseType = "json"
    }

}