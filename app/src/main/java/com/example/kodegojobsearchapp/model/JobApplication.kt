package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class JobApplication: Parcelable {
    // TODO: list all possible info for job application
    var jobApplicationID: String = ""
    var jobID: String = ""
    var applicantID: String = ""
}