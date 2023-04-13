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
    suspend fun updateApplicant(applicant: Applicant, fields: HashMap<String, Any?>): Boolean
    suspend fun deleteApplicant(applicant: Applicant): Boolean
}

class FirebaseApplicantDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseApplicantDAO{
    private val collection = FirebaseCollections.Applicants
    private val reference = fireStore.collection(collection)

    override suspend fun addApplicant(applicant: Applicant): Boolean {
        val document = reference.document(applicant.uID)
        applicant.applicantID = document.id
        val task = document.set(applicant.exportFirebaseApplicant(), SetOptions.merge())
        task.await()
        return if (task.isSuccessful){
            Log.i("Applicant Creation", "Successful")
            true
        }else{
            Log.e("Applicant Creation", task.exception?.message.toString())
            false
        }
    }

    /**
     * Gets Applicant model or Creates if it doesn't exist
     */
    override suspend fun getApplicant(uID: String): Applicant {
        val task = reference
            .document(uID)
            .get()
        task.await()
        return if (task.isSuccessful && task.result.data != null){
            Log.i("Applicant", task.result.toString())
            val applicant = task.result.toObject(Applicant::class.java)!!
            applicant.setUser(getUser(uID)!!)
            applicant
        }else{
            Log.e("GetApplicant", task.exception?.message.toString())
            val applicant = Applicant()
            applicant.uID = uID
            addApplicant(applicant)
            applicant.setUser(getUser(uID)!!)
            applicant
        }
    }

    override suspend fun getApplicants(): ArrayList<Applicant> {
//        TODO("Not yet implemented")
        val applicants: ArrayList<Applicant> = ArrayList()
        val task = reference.get()
        task.await()
        if (task.isSuccessful && task.result.documents.isNotEmpty()){
            Log.i("Applicants", task.result.documents.toString())
            task.result.documents.forEach { snapshot ->
                Log.i("Applicant Data", snapshot.data.toString())
                val applicant = snapshot.toObject(Applicant::class.java)!!
                applicant.setUser(getUser(applicant.uID)!!)
                Log.d("Applicant", applicant.toString())
                applicants.add(applicant)
            }
        }else{
            Log.e("GetApplicants", task.exception?.message.toString())
        }
        return applicants
    }

    override suspend fun updateApplicant(applicant: Applicant, fields: HashMap<String, Any?>): Boolean {
        val task = reference.document(applicant.applicantID)
            .update(fields)
        task.await()
        return task.isSuccessful
    }

    override suspend fun deleteApplicant(applicant: Applicant): Boolean {
//        TODO("Not yet implemented")
        val task = reference
            .document(applicant.applicantID)
            .delete()
        task.await()
        return task.isSuccessful
    }

}