package com.example.kodegojobsearchapp.utils

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.example.kodegojobsearchapp.JobDetailsActivity
import com.example.kodegojobsearchapp.api_model.JobDetailsData
import com.example.kodegojobsearchapp.databinding.DialogJobApplicationBinding
import com.example.kodegojobsearchapp.model.Applicant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class JobApplicationDialog(context: Context): AlertDialog(context) {
    private val binding: DialogJobApplicationBinding = DialogJobApplicationBinding.inflate(layoutInflater)
    private val progressDialog: ProgressDialog = ProgressDialog(context)
    private val lifecycleScope = CoroutineScope(Dispatchers.Main.immediate)
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
    }

    private fun submitApplication(){

    }
}