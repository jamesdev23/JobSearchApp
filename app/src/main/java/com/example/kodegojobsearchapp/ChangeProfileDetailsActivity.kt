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
import com.example.kodegojobsearchapp.model.User
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

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

        supportActionBar?.apply{
            title = "Change Profile Details"
            setDisplayHomeAsUpEnabled(true)
        }

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getApplicant(){
        lifecycleScope.launch {
            applicant = dao.getApplicant(Firebase.auth.currentUser!!.uid)
            setApplicantData()
        }
    }

    private fun setApplicantData(){
        with(binding){
            updateFirstName.setText(applicant.firstName)
            updateLastName.setText(applicant.lastName)
            updateAbout.setText(applicant.about)
            updateEducation.setText(applicant.education)
            updatePositionDesired.setText(applicant.positionDesired)
            updateSalary.setText(applicant.salary)
            updateSkills.setText(applicant.skills)
            updateLicenseCertification.setText(applicant.licensesOrCertifications)
            updateEmployment.setText(applicant.employment)
        }
    }

    private fun validateForm(){
        val updatedUser = User(applicant)
        val updatedUserFields: HashMap<String, Any?> = HashMap()
        val updatedApplicant = applicant.exportFirebaseApplicant()
        val updatedApplicantFields: HashMap<String, Any?> = HashMap()
        with(binding){
            updatedUser.firstName = updateFirstName.text.toString()
            updatedUser.lastName = updateLastName.text.toString()
            updatedApplicant.about = updateAbout.text.toString().trim()
            updatedApplicant.education = updateEducation.text.toString().trim()
            updatedApplicant.positionDesired = updatePositionDesired.text.toString().trim()
            updatedApplicant.salary = updateSalary.text.toString().trim()
            updatedApplicant.skills = updateSkills.text.toString().trim()
            updatedApplicant.licensesOrCertifications = updateLicenseCertification.text.toString().trim()
            updatedApplicant.employment = updateEmployment.text.toString().trim()
        }

        if (applicant.firstName != updatedUser.firstName){
            updatedUserFields["firstName"] = updatedUser.firstName
        }
        if (applicant.lastName != updatedUser.lastName){
            updatedUserFields["lastName"] = updatedUser.lastName
        }

        if (applicant.about != updatedApplicant.about){
            updatedApplicantFields["about"] = updatedApplicant.about
        }
        if (applicant.education != updatedApplicant.education){
            updatedApplicantFields["education"] = updatedApplicant.education
        }
        if (applicant.positionDesired != updatedApplicant.positionDesired){
            updatedApplicantFields["positionDesired"] = updatedApplicant.positionDesired
        }
        if (applicant.salary != updatedApplicant.salary){
            updatedApplicantFields["salary"] = updatedApplicant.salary
        }
        if (applicant.skills != updatedApplicant.skills){
            updatedApplicantFields["skills"] = updatedApplicant.skills
        }
        if (applicant.licensesOrCertifications != updatedApplicant.licensesOrCertifications){
            updatedApplicantFields["licensesOrCertifications"] = updatedApplicant.licensesOrCertifications
        }
        if (applicant.employment != updatedApplicant.employment){
            updatedApplicantFields["employment"] = updatedApplicant.employment
        }

        if (updatedUserFields.isNotEmpty() || updatedApplicantFields.isNotEmpty()){
            progressDialog.show()
            lifecycleScope.launch {
                if(dao.updateApplicant(applicant, updatedApplicantFields) && dao.updateUser(updatedUserFields)){
                    Toast.makeText(applicationContext, "Applicant Updated", Toast.LENGTH_SHORT).show()
                    backToApplicantViewPager()
                }else{
                    Toast.makeText(applicationContext, "Error updating Applicant", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
        }else{
            Snackbar.make(binding.root, "No Fields Changed", Toast.LENGTH_SHORT).show()
        }
    }

    //TODO: ActivityForResult
    private fun backToApplicantViewPager(){
        val intent = Intent(applicationContext, ApplicantViewPagerActivity::class.java)
        startActivity(intent)
        finish()
    }
}