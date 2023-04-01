package com.example.kodegojobsearchapp.firebase_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseApplicant(
    val userID: String = "",
    val applicantID: String = "",
    val profession: String = ""
): Parcelable
