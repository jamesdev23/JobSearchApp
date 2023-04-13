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
    var companyTelephone: String = "",
    var companyWebsite: String = ""
): Parcelable
