package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import com.example.kodegojobsearchapp.model.Employer
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

interface FirebaseEmployerDAO {
    suspend fun addEmployer(employer: Employer): Boolean
    suspend fun getEmployer(uID: String): Employer
    suspend fun getEmployers(): ArrayList<Employer>
    suspend fun updateEmployer(employer: Employer, fields: HashMap<String, Any?>): Boolean
    suspend fun deleteEmployer(employer: Employer): Boolean
}

class FirebaseEmployerDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseEmployerDAO{
    private val collection = FirebaseCollections.Employers
    private val reference = fireStore.collection(collection)

    override suspend fun addEmployer(employer: Employer): Boolean {
//        TODO("Not yet implemented")
        val document = reference.document(employer.uID)
        employer.employerID = document.id
        val task = document.set(employer.exportFirebaseEmployer(), SetOptions.merge())
        task.await()
        return if (task.isSuccessful){
            Log.i("Employer Creation", "Successful")
            true
        }else{
            Log.e("Employer Creation", task.exception?.message.toString())
            false
        }
    }

    /**
     * Gets Employer model or Creates if it doesn't exist
     */
    override suspend fun getEmployer(uID: String): Employer {
//        TODO("Not yet implemented")
        val task = reference
            .document(uID)
            .get()
        task.await()
        return if (task.isSuccessful && task.result.data != null){
            Log.i("Employer", task.result.toString())
            val employer = task.result.toObject(Employer::class.java)!!
            employer.setUser(getUser(uID)!!)
            employer
        }else{
            Log.e("GetEmployer", task.exception?.message.toString())
            val employer = Employer()
            employer.uID = uID
            addEmployer(employer)
            employer.setUser(getUser(uID)!!)
            employer
        }
    }

    override suspend fun getEmployers(): ArrayList<Employer> {
//        TODO("Not yet implemented")
        val employers: ArrayList<Employer> = ArrayList()
        val task = reference.get()
        task.await()
        if (task.isSuccessful && task.result.documents.isNotEmpty()){
            Log.i("Employers", task.result.documents.toString())
            task.result.documents.forEach { snapshot ->
                Log.i("Employer Data", snapshot.data.toString())
                val employer = snapshot.toObject(Employer::class.java)!!
                employer.setUser(getUser(employer.uID)!!)
                Log.d("Employer", employer.toString())
                employers.add(employer)
            }
        }else{
            Log.e("GetApplicants", task.exception?.message.toString())
        }
        return employers
    }

    override suspend fun updateEmployer(
        employer: Employer,
        fields: HashMap<String, Any?>
    ): Boolean {
//        TODO("Not yet implemented")
        val task = reference.document(employer.employerID)
            .update(fields)
        task.await()
        return task.isSuccessful
    }

    override suspend fun deleteEmployer(employer: Employer): Boolean {
//        TODO("Not yet implemented")
        val task = reference
            .document(employer.employerID)
            .delete()
        task.await()
        return task.isSuccessful
    }

}