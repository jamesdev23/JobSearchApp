package com.example.kodegojobsearchapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.databinding.ActivityChangeProfilePictureBinding
import com.example.kodegojobsearchapp.firebase.FirebaseStorageDAO
import com.example.kodegojobsearchapp.firebase.FirebaseStorageDAOImpl
import com.example.kodegojobsearchapp.utils.OpenDocumentContract
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

// TODO: profile picture function via Picasso and Intent
class ChangeProfilePictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfilePictureBinding
    private lateinit var dao: FirebaseStorageDAOImpl
    private lateinit var progressDialog: ProgressDialog

    private val openDocumentLauncher = registerForActivityResult(OpenDocumentContract()) { uri ->
        uri?.let { onImageSelected(it) } ?: Log.e("Document", "Image Pick Cancelled")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = FirebaseStorageDAOImpl(applicationContext)
        progressDialog = ProgressDialog(binding.root.context, R.string.updating_profile_picture)

        supportActionBar?.title = "Change Profile Picture"

        binding.btnUploadPicture.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("image/*"))
        }
        binding.btnCancel.setOnClickListener{
            onBackPressed()
        }
        loadProfilePicture()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun onImageSelected(uri: Uri){
        Log.i("URI", uri.toString())
        progressDialog.show()
        lifecycleScope.launch {
            if (dao.updateProfilePicture(uri)){
                loadProfilePicture()
                Toast.makeText(applicationContext, "Profile Picture Updated", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Error Updating Profile Picture", Toast.LENGTH_SHORT).show()
            }
            progressDialog.dismiss()
        }
    }

    private fun loadProfilePicture(){
        val photoUrl = Firebase.auth.currentUser!!.photoUrl
        Picasso
            .with(binding.root.context)
            .load(photoUrl)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .placeholder(R.drawable.job_search_logo_text_white)
            .error(R.drawable.job_search_logo_text_white)
            .into(binding.jobseekerProfilePicture)
    }
}