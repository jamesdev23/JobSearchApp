package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfileDetailsBinding
import com.example.kodegojobsearchapp.firebase.FirebaseApplicantDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

//TODO: implement update profile details using DAO

class ChangeProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileDetailsBinding
    private lateinit var dao: FirebaseApplicantDAOImpl
    private lateinit var applicant: Applicant
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(binding.root.context, R.string.updating_applicant_data)

        supportActionBar?.title = "Change Profile Details"

        dao = FirebaseApplicantDAOImpl(applicationContext)
        getApplicant()

        binding.btnApply.setOnClickListener { validateForm() }

        binding.btnCancal.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed() //TODO: Use ActivityForResult instead
        backToApplicantViewPager()
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

    private fun validateForm(){
        val updatedApplicant = applicant.exportFirebaseApplicant()
        val updatedFields: HashMap<String, Any?> = HashMap()
        with(binding){
            updatedApplicant.about = tvAboutText.text.toString().trim()
            updatedApplicant.education = tvEducationText.text.toString().trim()
            updatedApplicant.positionDesired = tvPositionText.text.toString().trim()
            updatedApplicant.salary = tvSalaryText.text.toString().trim()
            updatedApplicant.skills = tvSkillsText.text.toString().trim()
            updatedApplicant.licensesOrCertifications = tvLicenseText.text.toString().trim()
            updatedApplicant.employment = tvEmploymentText.text.toString().trim()
        }
        if (applicant.about != updatedApplicant.about){
            updatedFields["about"] = updatedApplicant.about
        }
        if (applicant.education != updatedApplicant.education){
            updatedFields["education"] = updatedApplicant.education
        }
        if (applicant.positionDesired != updatedApplicant.positionDesired){
            updatedFields["positionDesired"] = updatedApplicant.positionDesired
        }
        if (applicant.salary != updatedApplicant.salary){
            updatedFields["salary"] = updatedApplicant.salary
        }
        if (applicant.skills != updatedApplicant.skills){
            updatedFields["skills"] = updatedApplicant.skills
        }
        if (applicant.licensesOrCertifications != updatedApplicant.licensesOrCertifications){
            updatedFields["licensesOrCertifications"] = updatedApplicant.licensesOrCertifications
        }
        if (applicant.employment != updatedApplicant.employment){
            updatedFields["employment"] = updatedApplicant.employment
        }

        if (updatedFields.isNotEmpty()){
            progressDialog.show()
            lifecycleScope.launch {
                if(dao.updateApplicant(applicant, updatedFields)){
                    Toast.makeText(applicationContext, "Applicant Updated", Toast.LENGTH_SHORT).show()
                    backToApplicantViewPager()
                }else{
                    Toast.makeText(applicationContext, "Error updating Applicant", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
        }
    }

    //TODO: ActivityForResult
    private fun backToApplicantViewPager(){
        val intent = Intent(applicationContext, ApplicantViewPagerActivity::class.java)
        startActivity(intent)
        finish()
    }
}