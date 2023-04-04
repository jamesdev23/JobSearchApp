package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.kodegojobsearchapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirebaseUserDAO {
    suspend fun addUser(user: User): Boolean
    suspend fun getUser(uID: String): User?
    suspend fun updateUser(fields: HashMap<String, Any?>): Boolean
    suspend fun deleteUser()
}

open class FirebaseUserDAOImpl(internal val context: Context): FirebaseUserDAO{ //TODO: Implement
    internal val auth = FirebaseAuth.getInstance()
    internal val fireStore = FirebaseFirestore.getInstance()
    private val collection = FirebaseCollections.Users

    override suspend fun addUser(user: User): Boolean {
        val task = fireStore
            .collection(collection)
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
        val task = fireStore
            .collection(collection)
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
        val task = fireStore
            .collection(collection)
            .document(auth.currentUser!!.uid)
            .update(fields)
        task.await()
        return task.isSuccessful
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}