package com.example.kodegojobsearchapp.employer_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.databinding.FragmentEmployerJobsBinding

// TODO: (anyone) job listing implementation
class EmployerJobsFragment : Fragment() {
    private lateinit var _binding: FragmentEmployerJobsBinding
    private val binding get() = _binding

    init {
        if(this.arguments == null) {
            getTabInfo()
        }
    }
    private fun getTabInfo(){
        this.arguments = Bundle().apply {
            putString(FragmentKeys.TabName, "Jobs")
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
        _binding = FragmentEmployerJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

}