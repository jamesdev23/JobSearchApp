package com.example.kodegojobsearchapp.utils

import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.databinding.ItemJobApplicationBinding
import com.example.kodegojobsearchapp.model.JobApplication
import com.squareup.picasso.Picasso

fun ItemJobApplicationBinding.bind(application: JobApplication) {
    jobTitle.text = application.jobTitle
    jobCompany.text = application.companyName

    if (application.companyLogo.isNotEmpty()) {
        Picasso
            .with(root.context)
            .load(application.companyLogo)
//            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .placeholder(R.drawable.company_logo_placeholder)
            .error(R.drawable.company_logo_placeholder)
            .into(companyLogo)
    }
}