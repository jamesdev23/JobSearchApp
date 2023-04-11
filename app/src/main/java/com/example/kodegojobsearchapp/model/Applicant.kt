package com.example.kodegojobsearchapp.model

import com.example.kodegojobsearchapp.firebase_models.FirebaseApplicant

class Applicant(): User() {
    var applicantID: String = ""
    var occupation: String = ""
    var description: String = ""

    /**
     * Separate unnecessary inherited data from Super Class to prevent redundant data storage
     */
    fun exportFirebaseApplicant(): FirebaseApplicant{
        return FirebaseApplicant(uID, applicantID, occupation, description)
    }
}