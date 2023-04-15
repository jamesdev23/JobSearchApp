package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import com.example.kodegojobsearchapp.firebase_models.FirebaseApplicant
import kotlinx.parcelize.Parcelize

@Parcelize
class Applicant(): User(), Parcelable {
    var applicantID: String = ""
    var about: String = ""
    var education: String = ""
    var positionDesired: String = ""
    var salary: String = ""
    var skills: String = ""
    var licensesOrCertifications: String = ""
    var employment: String = ""

    /**
     * Separate unnecessary inherited data from Super Class to prevent redundant data storage
     */
    fun exportFirebaseApplicant(): FirebaseApplicant{
        return FirebaseApplicant(uID, applicantID, about, education, positionDesired, salary, skills, licensesOrCertifications, employment)
    }
}