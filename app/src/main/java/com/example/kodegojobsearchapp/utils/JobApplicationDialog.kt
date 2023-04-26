package com.example.kodegojobsearchapp.utils

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.kodegojobsearchapp.api_model.JobDetailsData
import com.example.kodegojobsearchapp.databinding.DialogJobApplicationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class JobApplicationDialog(context: Context, private val jobDetails: JobDetailsData): AlertDialog(context) {
    private lateinit var binding: DialogJobApplicationBinding
    private val progressDialog: ProgressDialog = ProgressDialog(context)
    private val lifecycleScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogJobApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setCancelable(false)
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }
}