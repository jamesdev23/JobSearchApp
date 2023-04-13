package com.example.kodegojobsearchapp.employer_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.applicant_viewpager.FragmentKeys
import com.example.kodegojobsearchapp.databinding.FragmentEmployerProfileBinding
import com.example.kodegojobsearchapp.firebase.FirebaseEmployerDAO
import com.example.kodegojobsearchapp.firebase.FirebaseEmployerDAOImpl
import com.example.kodegojobsearchapp.model.Employer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

// TODO: (anyone) employer profile implementation. hint: firebase db
// TODO: #2: add prompt to create/fill up profile info (implementation is up to you) -nico
class EmployerProfileFragment : Fragment() {
    private lateinit var _binding: FragmentEmployerProfileBinding
    private val binding get() = _binding
    private lateinit var dao: FirebaseEmployerDAO
    private lateinit var employer: Employer

    init {
        if(this.arguments == null) {
            getTabInfo()
        }
    }
    private fun getTabInfo(){
        this.arguments = Bundle().apply {
            putString(FragmentKeys.TabName, "Profile")
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
        _binding = FragmentEmployerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = FirebaseEmployerDAOImpl(requireContext())
        getEmployer()
    }

    private fun getEmployer(){
        lifecycleScope.launch {
            employer = dao.getEmployer(Firebase.auth.currentUser!!.uid)
            setEmployerData()
        }
    }

    private fun setEmployerData(){
        with(binding){
            name.text = "${employer.firstName} ${employer.lastName}"
            company.text = employer.companyName
            position.text = employer.position
            companyAddress.text = employer.companyAddress
            companyTelephone.text = employer.companyTelephone
            companyWebsite.text = employer.companyWebsite
        }
    }
}