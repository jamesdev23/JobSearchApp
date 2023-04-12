package com.example.kodegojobsearchapp.firebase_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseApplicant(
    val userID: String = "",
    val applicantID: String = "",
    val about: String = "",
    val education: String = "",
    val positionDesired: String = "",
    val salary: String = "",
    val skills: String = "",
    val licensesOrCertifications: String = "",
    val employment: String = ""
): Parcelable
