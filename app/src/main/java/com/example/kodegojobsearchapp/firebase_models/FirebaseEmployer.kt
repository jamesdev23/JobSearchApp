package com.example.kodegojobsearchapp.firebase_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseEmployer(
    val userID: String = "",
    val employerID: String = "",
    var companyName: String = "",
    var position: String = "",
    var companyAddress: String = "",
    var telephoneNumber: String = "",
    var website: String = ""
): Parcelable
