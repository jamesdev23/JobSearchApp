package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.kodegojobsearchapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

interface FirebaseUserDAO {
    suspend fun addUser(user: User): Boolean
    suspend fun getUser(uID: String): User?
    suspend fun updateUser(fields: HashMap<String, Any?>): Boolean
    suspend fun updateUserProfile(fields: HashMap<String, Any?>): Boolean
    suspend fun deleteUser(user: User): Boolean
}

open class FirebaseUserDAOImpl(internal val context: Context): FirebaseUserDAO{
    internal val auth = FirebaseAuth.getInstance()
    internal val fireStore = FirebaseFirestore.getInstance()
    private val collection = FirebaseCollections.Users
    private val reference = fireStore.collection(collection)

    override suspend fun addUser(user: User): Boolean {
        val task = reference
            .document(user.uID)
            .set(user, SetOptions.merge())
        task.await()
        return if (task.isSuccessful){
            Toast.makeText(context, "User Registration Successful", Toast.LENGTH_SHORT).show()
            true
        }else{
            Log.e("User Registration", task.exception!!.message.toString())
            false
        }
    }

    override suspend fun getUser(uID: String): User? {
        val task = reference
            .document(uID)
            .get()
        task.await()
        Log.d("Get User", task.result.toString(), task.exception)
        return if (task.isSuccessful && task.result.data != null){
            Log.i("User Document Retrieved", task.result.toString())
            task.result.toObject(User::class.java)!!
        }else{
            Log.e("User Error", task.exception?.message.toString())
            null
        }
    }

    override suspend fun updateUser(fields: HashMap<String, Any?>): Boolean {
        val task = reference
            .document(auth.currentUser!!.uid)
            .update(fields)
        task.await()
        return task.isSuccessful
    }

    override suspend fun updateUserProfile(fields: HashMap<String, Any?>): Boolean {
//        TODO("Not yet implemented")
        val user = auth.currentUser!!
        val request = userProfileChangeRequest {
            if (fields.containsKey("firstName") && fields.containsKey("lastName")){
                displayName = "${fields["firstName"]} ${fields["lastName"]}"
            }
            if (fields.containsKey("photoUri")){
                photoUri = fields["photoUri"].toString().toUri()
            }
        }
        val task = user.updateProfile(request)
        task.await()
        return if (task.isSuccessful){
            true
        }else{
            Log.e("UserProfile Update", task.exception?.message.toString())
            false
        }
    }

    override suspend fun deleteUser(user: User): Boolean {
//        TODO("Not yet implemented")
        val task = reference.document(user.uID).delete()
        task.await()
        return task.isSuccessful
    }
}