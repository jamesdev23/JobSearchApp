package com.example.kodegojobsearchapp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.databinding.DialogueProgressBinding

class ProgressDialog(context: Context, private val progressText: Int = R.string.please_wait): Dialog(context) {
    private lateinit var binding: DialogueProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogueProgressBinding.inflate(layoutInflater)
        binding.progressText.setText(progressText)
        setContentView(binding.root)
        setCancelable(false)
    }
}