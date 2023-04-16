package com.example.kodegojobsearchapp.applicant_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.databinding.FragmentProfileBinding
import com.example.kodegojobsearchapp.firebase.FirebaseApplicantDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

// TODO: applicant profile implementation. hint: grab info from firebase db
// TODO: #2: add prompt to create/fill up profile info (implementation is up to you) -nico

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: FirebaseApplicantDAOImpl
    private lateinit var applicant: Applicant

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = FirebaseApplicantDAOImpl(requireContext())
        getApplicant()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun getApplicant(){
        lifecycleScope.launch {
            applicant = dao.getApplicant(Firebase.auth.currentUser!!.uid)
            setApplicantData()
        }
    }

    private fun setApplicantData(){
        with(binding){
            name.text = "${applicant.firstName} ${applicant.lastName}"
            tvAboutText.text = applicant.about
            tvEducationText.text = applicant.education
            tvPositionText.text = applicant.positionDesired
            tvSalaryText.text = applicant.salary
            tvSkillsText.text = applicant.skills
            tvLicenseText.text = applicant.licensesOrCertifications
            tvEmploymentText.text = applicant.employment

            loadProfilePicture(applicant.image)
        }
    }

    private fun loadProfilePicture(applicantImage: String){

        if (applicantImage.isEmpty()){
            // do nothing
        } else{
            Picasso
                .with(requireContext())
                .load(applicantImage)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.tvProfilePicture)
        }

    }
}