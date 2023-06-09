package com.example.kodegojobsearchapp.applicant_viewpager

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.ChangeProfileDetailsActivity
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.databinding.FragmentHomeBinding
import com.example.kodegojobsearchapp.databinding.FragmentProfileBinding
import com.example.kodegojobsearchapp.firebase.FirebaseApplicantDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: FirebaseApplicantDAOImpl
    private var applicant: Applicant = Applicant()

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

            if(applicant.fullName().isEmpty()){
                val toast = Toast.makeText(
                    requireContext(),
                    "Your profile is empty. Fill out the details here and press \"Update\".",
                    Toast.LENGTH_LONG)

                toast.show()

                val intent = Intent(requireContext(), ChangeProfileDetailsActivity::class.java)
                startActivity(intent)
            }else{
                setApplicantData()
            }
        }
    }

    private fun setApplicantData(){
        with(binding){
            profileName.text = applicant.fullName()
            profileAbout.text = applicant.about
            profileEducation.text = applicant.education
            profileCurrentPosition.text = applicant.positionDesired
            profileSalary.text = applicant.salary
            profileSkills.text = applicant.skills
            profileLicenseCertification.text = applicant.licensesOrCertifications
            profileJobExperience.text = applicant.employment

            loadProfilePicture(applicant.image)
        }
    }

    private fun loadProfilePicture(applicantImage: String){
        if (applicantImage.isNotEmpty()){
            Picasso
                .with(requireContext())
                .load(applicantImage)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.profilePicture)
        }
    }

}