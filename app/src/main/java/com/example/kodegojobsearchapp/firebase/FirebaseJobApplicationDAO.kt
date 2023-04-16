package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.kodegojobsearchapp.model.Applicant
import com.example.kodegojobsearchapp.model.JobApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

interface FirebaseJobApplicationDAO {
    suspend fun addJobApplication(jobApplication: JobApplication): Boolean
    suspend fun getJobApplication(jobApplicationID: String): JobApplication?
    suspend fun getJobApplications(applicant: Applicant): ArrayList<JobApplication>
    suspend fun updateJobApplication(jobApplication: JobApplication, fields: HashMap<String, Any?>): Boolean
    suspend fun deleteJobApplication(jobApplication: JobApplication): Boolean
}

class FirebaseJobApplicationDAOImpl(context: Context): FirebaseApplicantDAOImpl(context), FirebaseJobApplicationDAO{
    private val collection = FirebaseCollections.JobApplications

    override suspend fun addJobApplication(jobApplication: JobApplication): Boolean {
//        TODO("Not yet implemented")
        val reference = reference.document(jobApplication.applicantID).collection(collection)
        val document = reference.document()
        jobApplication.jobApplicationID = document.id
        val task = document.set(jobApplication, SetOptions.merge())
        task.await()
        return if (task.isSuccessful){
            Toast.makeText(context, "JobApplication Registration Successful", Toast.LENGTH_SHORT).show()
            true
        }else{
            Log.e("JobApplication Registration", task.exception!!.message.toString())
            false
        }
    }

    override suspend fun getJobApplication(jobApplicationID: String): JobApplication? {
        TODO("Not yet implemented")
//        val reference = reference.document(jobApplication.applicantID).collection(collection) //TODO: Directory
        val task = reference
            .document(jobApplicationID)
            .get()
        task.await()
        return if (task.isSuccessful && task.result.data != null){
            Log.i("JobApplication Retrieved", task.result.toString())
            task.result.toObject(JobApplication::class.java)!!
        }else{
            Log.e("JobApplication Error", task.exception?.message.toString())
            null
        }
    }

    override suspend fun getJobApplications(applicant: Applicant): ArrayList<JobApplication> {
//        TODO("Not yet implemented")
        val reference = reference.document(applicant.applicantID).collection(collection)
        val jobApplications: ArrayList<JobApplication> = ArrayList()
        val task = reference.get()
        task.await()
        if (task.isSuccessful && task.result.documents.isNotEmpty()){
            Log.i("Applications", task.result.documents.toString())
            task.result.documents.forEach { document ->
                Log.i("Application Data", document.data.toString())
                val jobApplication = document.toObject(JobApplication::class.java)!!
                jobApplications.add(jobApplication)
            }
        }else{
            Log.e("GetApplications", task.exception?.message.toString())
        }
        return jobApplications
    }

    override suspend fun updateJobApplication(jobApplication: JobApplication, fields: HashMap<String, Any?>): Boolean {
//        TODO("Not yet implemented")
        val reference = reference.document(jobApplication.applicantID).collection(collection)
        val task = reference.document(jobApplication.jobApplicationID)
            .update(fields)
        task.await()
        return task.isSuccessful
    }

    override suspend fun deleteJobApplication(jobApplication: JobApplication): Boolean {
//        TODO("Not yet implemented")
        val reference = reference.document(jobApplication.applicantID).collection(collection)
        val task = reference
            .document(jobApplication.jobApplicationID)
            .delete()
        task.await()
        return task.isSuccessful
    }
}