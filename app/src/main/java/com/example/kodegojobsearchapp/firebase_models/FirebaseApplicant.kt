package com.example.kodegojobsearchapp.firebase_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseApplicant(
    val userID: String = "",
    val applicantID: String = "",
    var about: String = "",
    var education: String = "",
    var positionDesired: String = "",
    var salary: String = "",
    var skills: String = "",
    var licensesOrCertifications: String = "",
    var employment: String = ""
): Parcelable
