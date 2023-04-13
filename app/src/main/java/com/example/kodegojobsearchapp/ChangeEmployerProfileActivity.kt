package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivityChangeEmployerProfileBinding
import com.example.kodegojobsearchapp.employer_viewpager.EmployerActivity
import com.example.kodegojobsearchapp.firebase.FirebaseEmployerDAO
import com.example.kodegojobsearchapp.firebase.FirebaseEmployerDAOImpl
import com.example.kodegojobsearchapp.model.Employer
import com.example.kodegojobsearchapp.model.User
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChangeEmployerProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmployerProfileBinding
    private lateinit var dao: FirebaseEmployerDAOImpl
    private lateinit var employer: Employer
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmployerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = FirebaseEmployerDAOImpl(applicationContext)
        getEmployer()
        progressDialog = ProgressDialog(binding.root.context, R.string.updating_employer_data)

        binding.btnApply.setOnClickListener { validateForm() }

        binding.btnCancal.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed() //TODO: Use ActivityForResult instead
        backToEmployerViewPager()
    }

    private fun getEmployer(){
        lifecycleScope.launch {
            employer = dao.getEmployer(Firebase.auth.currentUser!!.uid)
            setEmployerData()
        }
    }

    private fun setEmployerData(){
        with(binding){
            firstName.setText(employer.firstName)
            lastName.setText(employer.lastName)
            company.setText(employer.companyName)
            position.setText(employer.position)
            companyAddress.setText(employer.companyAddress)
            companyTelephone.setText(employer.companyTelephone)
            companyWebsite.setText(employer.companyWebsite)
        }
    }

    private fun validateForm(){
        val updatedUser = User(employer)
        val updatedUserFields: HashMap<String, Any?> = HashMap()
        val updatedEmployer = employer.exportFirebaseEmployer()
        val updatedEmployerFields: HashMap<String, Any?> = HashMap()

        with(binding){
            updatedUser.firstName = firstName.text.toString()
            updatedUser.lastName = lastName.text.toString()
            updatedEmployer.companyName = company.text.toString()
            updatedEmployer.position = position.text.toString()
            updatedEmployer.companyAddress = companyAddress.text.toString()
            updatedEmployer.companyTelephone = companyTelephone.text.toString()
            updatedEmployer.companyWebsite = companyWebsite.text.toString()
        }

        /**
         * Check Updated Fields from User
         */
        if (employer.firstName != updatedUser.firstName){
            updatedUserFields["firstName"] = updatedUser.firstName
        }
        if (employer.lastName != updatedUser.lastName){
            updatedUserFields["lastName"] = updatedUser.lastName
        }

        /**
         * Check Updated Fields from Employer
         */
        if (employer.companyName != updatedEmployer.companyName){
            updatedEmployerFields["companyName"] = updatedEmployer.companyName
        }
        if (employer.position != updatedEmployer.position){
            updatedEmployerFields["position"] = updatedEmployer.position
        }
        if (employer.companyAddress != updatedEmployer.companyAddress){
            updatedEmployerFields["companyAddress"] = updatedEmployer.companyAddress
        }
        if (employer.companyTelephone != updatedEmployer.companyTelephone){
            updatedEmployerFields["companyTelephone"] = updatedEmployer.companyTelephone
        }
        if (employer.companyWebsite != updatedEmployer.companyWebsite){
            updatedEmployerFields["companyWebsite"] = updatedEmployer.companyWebsite
        }

        /**
         * Check if there are updated Fields
         */
        if (updatedUserFields.isNotEmpty() || updatedEmployerFields.isNotEmpty()){
            progressDialog.show()
            lifecycleScope.launch {
                if (
                    dao.updateEmployer(employer, updatedEmployerFields) &&
                    dao.updateUser(updatedUserFields)
                ){
                    Toast.makeText(applicationContext, "Applicant Updated", Toast.LENGTH_SHORT).show()
                    backToEmployerViewPager()
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
    private fun backToEmployerViewPager(){
        val intent = Intent(applicationContext, EmployerActivity::class.java)
        startActivity(intent)
        finish()
    }
}