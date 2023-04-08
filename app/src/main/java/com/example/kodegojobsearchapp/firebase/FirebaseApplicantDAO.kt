package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import com.example.kodegojobsearchapp.model.Applicant
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

interface FirebaseApplicantDAO {
    suspend fun addApplicant(applicant: Applicant): Boolean
    suspend fun getApplicant(uID: String): Applicant
    suspend fun getApplicants(): ArrayList<Applicant>
    suspend fun updateApplicant(fields: HashMap<String, Any?>): Boolean //TODO: Not yet implemented
    suspend fun deleteApplicant()
}

class FirebaseApplicantDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseApplicantDAO{
    private val collection = FirebaseCollections.Applicants
    override suspend fun addApplicant(applicant: Applicant): Boolean {
        //TODO: Double-check process
        val reference = fireStore
            .collection(collection)
            .document()
        applicant.applicantID = reference.id
        val task = reference.set(applicant.exportFirebaseApplicant(), SetOptions.merge())
        task.await()
        return if (task.isSuccessful){
            Log.i("Applicant Creation", "Successful")
            true
        }else{
            Log.e("Applicant Creation", task.exception?.message.toString())
            false
        }
    }

    override suspend fun getApplicant(uID: String): Applicant {
        TODO("Not yet implemented")
    }

    override suspend fun getApplicants(): ArrayList<Applicant> {
        TODO("Not yet implemented")
    }

    override suspend fun updateApplicant(fields: HashMap<String, Any?>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteApplicant() {
        TODO("Not yet implemented")
    }

}