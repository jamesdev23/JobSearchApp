package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.adapter.ListJobApplicationAdapter
import com.example.kodegojobsearchapp.dao.KodegoJobSearchApplication
import com.example.kodegojobsearchapp.databinding.FragmentJobApplicationBinding
import com.example.kodegojobsearchapp.firebase.FirebaseJobApplicationDAOImpl
import com.example.kodegojobsearchapp.model.JobApplication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class JobApplicationFragment : Fragment() {

    private var _binding: FragmentJobApplicationBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: FirebaseJobApplicationDAOImpl
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
        _binding = FragmentJobApplicationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = FirebaseJobApplicationDAOImpl(requireContext())

        getApplications()
    }

    private fun getApplications(){
        Log.d("DEBUG","RECENT JOB APPLICATIONS")
        binding.loadingData.visibility = View.VISIBLE
        binding.listRecentJobs.visibility = View.GONE
        lifecycleScope.launch {
            val applicant = dao.getApplicant(Firebase.auth.currentUser!!.uid)
            applications.addAll(dao.getJobApplications(applicant))
            setApplicationsRecyclerView()
        }
    }

    private fun setApplicationsRecyclerView(){
        jobApplicationsAdapter = ListJobApplicationAdapter(applications)
        binding.listRecentJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.listRecentJobs.adapter = jobApplicationsAdapter

        binding.loadingData.visibility = View.GONE
        binding.listRecentJobs.visibility = View.VISIBLE
    }
}