package com.example.kodegojobsearchapp.model

import com.example.kodegojobsearchapp.firebase_models.FirebaseApplicant

class Applicant(): User() {
    var applicantID: String = ""
    var occupation: String = ""
    var description: String = ""

    fun exportFirebaseApplicant(): FirebaseApplicant{
        return FirebaseApplicant(uID, applicantID, occupation, description)
    }
}