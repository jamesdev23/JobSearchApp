package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import com.example.kodegojobsearchapp.firebase_models.FirebaseEmployer
import kotlinx.parcelize.Parcelize

@Parcelize
class Employer: User(), Parcelable {
    var employerID: String = ""
    var companyName: String = ""
    var position: String = ""
    var companyAddress: String = ""
    var telephoneNumber: String = ""
    var website: String = ""

    fun exportFirebaseEmployer(): FirebaseEmployer{
        return FirebaseEmployer(uID, employerID, companyName, position, companyAddress, telephoneNumber, website)
    }
}