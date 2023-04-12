package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfileDetailsBinding
import com.example.kodegojobsearchapp.firebase.FirebaseApplicantDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

//TODO: implement update profile details using DAO

class ChangeProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileDetailsBinding
    private lateinit var dao: FirebaseApplicantDAOImpl
    private lateinit var applicant: Applicant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Change Profile Details"

        dao = FirebaseApplicantDAOImpl(applicationContext)
        getApplicant()

        binding.btnCancal.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getApplicant(){
        lifecycleScope.launch {
            applicant = dao.getApplicant(Firebase.auth.currentUser!!.uid)
            setApplicantData()
        }
    }

    private fun setApplicantData(){
        with(binding){
            tvAboutText.setText(applicant.about)
            tvEducationText.setText(applicant.education)
            tvPositionText.setText(applicant.positionDesired)
            tvSalaryText.setText(applicant.salary)
            tvSkillsText.setText(applicant.skills)
            tvLicenseText.setText(applicant.licensesOrCertifications)
            tvEmploymentText.setText(applicant.employment)
        }
    }
}