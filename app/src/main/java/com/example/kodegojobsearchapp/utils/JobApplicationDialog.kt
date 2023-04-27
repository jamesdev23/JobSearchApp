package com.example.kodegojobsearchapp.utils

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.example.kodegojobsearchapp.JobDetailsActivity
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
        if (resume != null){
            val application = JobApplication(jobDetails.jobId, applicant.applicantID)
            progressDialog.show()
            lifecycleScope.launch {
                val uri = storage.uploadDocument(resume!!)
                if (uri != null){
                    application.resume = uri.toString()
                    application.email = binding.applyEmail.text.toString()
                    application.contactNumber = binding.applyContactNumber.text.toString()
                    application.coverLetter = binding.applyCoverLetter.text.toString()
                    if(dao.addJobApplication(application)){
                        Toast.makeText(
                            context,
                            "Application Successfully Submitted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            context,
                            "Error Submitting Application",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dismiss()
                }else{
                    Toast.makeText(context, "Error Uploading Resume", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
        }
    }
}