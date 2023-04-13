package com.example.kodegojobsearchapp.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await

interface FirebaseStorageDAO {
    suspend fun uploadImage(uri: Uri): Uri?
    suspend fun updateProfilePicture(uri: Uri): Boolean
    suspend fun deleteImage(url: String): Boolean
}

class FirebaseStorageDAOImpl(context: Context): FirebaseUserDAOImpl(context), FirebaseStorageDAO{
    internal val firebaseStorage = FirebaseStorage.getInstance()
    private val parentTree = auth.currentUser!!.uid
    private val imageTree = "images"

    override suspend fun uploadImage(uri: Uri): Uri? {
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

    /**
     * Uploads the selected Profile Picture then Deletes the old one
     */
    override suspend fun updateProfilePicture(uri: Uri): Boolean { //TODO: Optimize
        val storageUri = uploadImage(uri)
        if (storageUri != null){
            val user = auth.currentUser!!
            Log.d("User PhotoUrl", user.photoUrl.toString())
            if (user.photoUrl != null && user.photoUrl.toString().contains("firebasestorage")) {
                val oldReference = firebaseStorage.getReferenceFromUrl(user.photoUrl.toString())
                val newReference = firebaseStorage.getReferenceFromUrl(storageUri.toString())
                if (oldReference != newReference) {
                    deleteImage(user.photoUrl.toString())
                }
            }
            val userProfileChangeMap: HashMap<String, Any?> = hashMapOf("photoUri" to storageUri)
            return if (updateUserProfile(userProfileChangeMap)){
                val updateImageMap = HashMap<String, Any?>()
                updateImageMap["image"] = storageUri.toString()
                updateUser(updateImageMap)
            }else{
                false
            }
        }else{
            return false
        }
    }

    override suspend fun deleteImage(url: String): Boolean {
        return try {
            val imageReference = firebaseStorage.getReferenceFromUrl(url)
            val task = imageReference.delete()
            task.await()
            Log.d("Photo Deletion", task.exception?.message.toString())
            task.isSuccessful
        }catch (e: StorageException){
            Log.e("Photo Deletion", e.message.toString())
            false
        }
    }
}