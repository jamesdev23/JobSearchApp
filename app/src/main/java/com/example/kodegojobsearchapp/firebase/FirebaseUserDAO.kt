package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.util.Log
import com.example.kodegojobsearchapp.model.User

interface FirebaseUserDAO {
    suspend fun addUser(user: User): Boolean
    suspend fun registerAccount(firstName: String, lastName: String, email: String, password: String): Boolean /** For Email & Password Login */
    suspend fun getUser(uID: String): User?
    suspend fun updateUser(fields: HashMap<String, Any>): Boolean
    suspend fun deleteUser()
}

open class FirebaseUserDAOImpl(context: Context): FirebaseUserDAO{ //TODO: Implement
//    internal val auth = FirebaseAuth.getInstance()
//    internal val fireStore = FirebaseFirestore.getInstance()

    private val collection = FirebaseCollections.Users
    override suspend fun addUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun registerAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(uID: String): User? {
        TODO("Not yet implemented")
//        val task = fireStore
//            .collection(collection)
//            .document(uID)
//            .get()
//        task.await()
//        Log.d("Get User", task.result.toString(), task.exception)
//        return if (task.isSuccessful && task.result.data != null){
//            Log.i("Account Document Retrieved", task.result.toString())
//            task.result.toObject(User::class.java)!!
//        }else{
//            Log.e("Account Error", task.exception!!.message.toString())
//            null
//        }
    }

    override suspend fun updateUser(fields: HashMap<String, Any>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}