package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobApplication(
    var jobID: String = "",
    var applicantID: String = ""
): Parcelable {
    // TODO: list all possible info for job application
    var jobApplicationID: String = ""
    var email: String = ""
    var contactNumber: String = ""
    var coverLetter: String = ""
    var resume: String = ""
}

enum class ApplicationType{

}