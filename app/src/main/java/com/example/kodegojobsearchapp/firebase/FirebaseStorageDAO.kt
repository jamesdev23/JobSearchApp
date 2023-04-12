package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await

interface FirebaseStorageDAO {
    suspend fun uploadProfilePicture(uri: Uri): Uri?
    suspend fun updateProfilePicture(uri: Uri): Boolean
    suspend fun deleteProfilePicture(url: String): Boolean
}

class FirebaseStorageDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseStorageDAO{
    internal val firebaseStorage = FirebaseStorage.getInstance()
    private val parentTree = auth.currentUser!!.uid
    private val imageTree = "images"

    override suspend fun uploadProfilePicture(uri: Uri): Uri? {
        val reference = firebaseStorage
            .getReference(parentTree)
            .child(imageTree)
            .child(uri.lastPathSegment.toString())
        val task: UploadTask = reference.putFile(uri)
        task.await()
        if (task.isSuccessful){
            Log.d("Profile Upload", task.result.toString())
//            val urlTask = reference.downloadUrl
            val urlTask = task.snapshot.metadata!!.reference!!.downloadUrl
            urlTask.await()
            if (urlTask.isSuccessful){
                Log.d("DownloadURL", urlTask.result.toString())
                return urlTask.result
            }else{
                Log.e("DownloadURL", task.exception!!.message!!)
                return null
            }
        }else{
            Log.e("Profile Upload", task.exception!!.message!!)
            return null
        }
    }

    override suspend fun updateProfilePicture(uri: Uri): Boolean { //TODO: Optimize
        val storageUri = uploadProfilePicture(uri)
        if (storageUri != null){
            val user = auth.currentUser!!
            Log.d("User PhotoUrl", user.photoUrl.toString())
            if (user.photoUrl != null && user.photoUrl.toString().contains("firebasestorage")) {
                val oldReference = firebaseStorage.getReferenceFromUrl(user.photoUrl.toString())
                val newReference = firebaseStorage.getReferenceFromUrl(storageUri.toString())
                if (oldReference != newReference) {
                    deleteProfilePicture(user.photoUrl.toString())
                }
            }
            val photoUriUpdate = userProfileChangeRequest {
                photoUri = storageUri
            }
            val task = user.updateProfile(photoUriUpdate)
            task.await()
            return if (task.isSuccessful){
                val updateImageMap = HashMap<String, Any?>()
                updateImageMap["image"] = storageUri.toString()
                updateUser(updateImageMap)
            }else{
                Log.e("PhotoUpdate", task.exception?.message.toString())
                false
            }
        }else{
            return false
        }
    }

    override suspend fun deleteProfilePicture(url: String): Boolean {
        try {
            val imageReference = firebaseStorage.getReferenceFromUrl(url)
            val task = imageReference.delete()
            task.await()
            Log.d("Photo Deletion", task.exception?.message.toString())
            return task.isSuccessful
        }catch (e: StorageException){
            Log.e("Photo Deletion", e.message.toString())
            return false
        }
    }
}