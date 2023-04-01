package com.example.kodegojobsearchapp.model

import com.example.kodegojobsearchapp.firebase_models.FirebaseApplicant

class Applicant(): User() {
    var applicantID: String = ""
    var profession: String = ""

    fun exportFirebaseApplicant(): FirebaseApplicant{
        return FirebaseApplicant(uID, applicantID, profession) //TODO: Confirm ReferenceID user (uID)
    }
}