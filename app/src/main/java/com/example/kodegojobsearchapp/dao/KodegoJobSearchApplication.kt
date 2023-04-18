package com.example.kodegojobsearchapp.dao

import android.app.Application

class KodegoJobSearchApplication: Application() {
    val jobDetailsDatabase by lazy {
        JobDetailsDatabase.getInstance(this)
    }
    val jobListingDatabase by lazy {
        JobListingDatabase.getInstance(this)
    }
}