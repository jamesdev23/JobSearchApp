package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import com.example.kodegojobsearchapp.model.Applicant

interface FirebaseApplicantDAO {
    suspend fun addApplicant(applicant: Applicant): Boolean
    suspend fun getApplicant(uID: String): Applicant
    suspend fun getApplicants(): ArrayList<Applicant>
    suspend fun updateApplicant(fields: HashMap<String, Any?>): Boolean //TODO: Not yet implemented
    suspend fun deleteApplicant()
}

class FirebaseApplicantDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseApplicantDAO{
    private val collection = FirebaseCollections.Applicant
    override suspend fun addApplicant(applicant: Applicant): Boolean {
        TODO("Not yet implemented")
//        val reference = fireStore
//            .collection(collection)
//            .document()
//        applicant.applicantID = reference.id
//        val task = reference.set(applicant.exportFirebaseApplicant(), SetOptions.merge())
//        task.await()
//        Log.d("Add Applicant", task.result.toString(), task.exception)
//        return if (task.isSuccessful){
//            Log.i("Profile Creation", "Successful")
//            true
//        }else{
//            Log.e("Profile Creation", task.exception!!.message.toString())
//            false
//        }
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