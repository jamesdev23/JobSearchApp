package com.example.kodegojobsearchapp.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kodegojobsearchapp.api_model.JobDetailsData
import com.example.kodegojobsearchapp.databinding.DialogJobApplicationBinding
import com.example.kodegojobsearchapp.firebase.FirebaseJobApplicationDAOImpl
import com.example.kodegojobsearchapp.firebase.FirebaseStorageDAOImpl
import com.example.kodegojobsearchapp.model.Applicant
import com.example.kodegojobsearchapp.model.JobApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobApplicationDialog(context: Context): AlertDialog(context) {
    private val binding: DialogJobApplicationBinding = DialogJobApplicationBinding.inflate(layoutInflater)
    private val progressDialog: ProgressDialog = ProgressDialog(context)
    private val lifecycleScope = CoroutineScope(Dispatchers.Main.immediate)
    private val storage = FirebaseStorageDAOImpl(context)
    private val dao = FirebaseJobApplicationDAOImpl(context)
    private lateinit var applicant: Applicant
    private lateinit var jobDetails: JobDetailsData
    private var resume: Uri? = null
    var emailIntent: Intent? = null

    init {
        create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setCancelable(false)
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        with(binding){
            btnSubmit.setOnClickListener { submitApplication() }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    override fun show() {
        super.show()
        resume = null
        emailIntent = null
    }

    fun show(applicant: Applicant, jobDetailsData: JobDetailsData){
        this.applicant = applicant
        jobDetails = jobDetailsData
        show()
    }

    fun onSelectResume(listener: OnClickListener){
        binding.btnSelectResume.setOnClickListener(listener)
    }

    fun onDocumentSelected(uri: Uri){
        Log.d("URI", uri.lastPathSegment.toString())
        resume = uri
        binding.textSelectFileName.text = resume!!.lastPathSegment.toString()
    }

    private fun submitApplication(){
        binding.btnSubmit.isEnabled = false

        when {
            binding.applyEmail.text.isNullOrEmpty() ->
                toast("Email is Empty", context)

            binding.applyContactNumber.text.isNullOrEmpty() ->
                toast("Contact number is Empty", context)

            binding.applyCoverLetter.text.isNullOrEmpty() ->
                toast("Cover Letter is Empty", context)

            resume == null ->
                toast("Please Upload a Resume", context)

            else -> {
                val application = JobApplication(jobDetails.jobId, applicant.applicantID)
                progressDialog.show()
                lifecycleScope.launch {
                    val uri = storage.uploadDocument(resume!!)
                    if (uri != null) {
                        application.resume = uri.toString()
                        application.jobTitle = jobDetails.jobTitle
                        application.companyName = jobDetails.employerName
                        application.companyLogo = jobDetails.employerLogo ?: ""
                        application.email = binding.applyEmail.text.toString()
                        application.contactNumber = binding.applyContactNumber.text.toString()
                        application.coverLetter = binding.applyCoverLetter.text.toString()
                        if (dao.addJobApplication(application)) {
                            prepareEmail(application)
                            Toast.makeText(
                                context,
                                "Application Successfully Submitted",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Error Submitting Application",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        dismiss()
                    } else {
                        Toast.makeText(context, "Error Uploading Resume", Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.dismiss()
                    binding.btnSubmit.isEnabled = true
                }
            }
        }

    }

    private fun prepareEmail(application: JobApplication){
        val subject = StringBuilder()
        subject.append("Application for ")
        subject.append(jobDetails.jobTitle)
        subject.append(" under ")
        subject.append(jobDetails.employerName)

        val message = StringBuilder()
        message.append("Email Used: ")
        message.append(application.email).appendLine()
        message.append("Contact Number Used: ")
        message.append(application.contactNumber).appendLine().appendLine()
        message.append("Company/Employer Name: ")
        message.append(application.companyName).appendLine()
        message.append("Job Title: ")
        message.append(application.jobTitle).appendLine()
        message.append("Cover Letter: ")
        message.append(application.coverLetter).appendLine()
        message.append("Resume Submitted: ")
        message.append("TBA")
//        message.append(storage.getDocumentUri(application.resume)) //TODO: Fix

        val intent = Intent(Intent.ACTION_SEND)
        intent.setDataAndType(Uri.parse("mailto:"), "text/plain")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(application.email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject.toString())
        intent.putExtra(Intent.EXTRA_TEXT, message.toString())

        emailIntent = intent
    }

    fun toast(message: String, context: Context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}