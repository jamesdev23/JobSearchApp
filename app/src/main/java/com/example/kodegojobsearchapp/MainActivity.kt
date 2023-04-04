package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.kodegojobsearchapp.applicant_viewpager.ApplicantViewPagerActivity
import com.example.kodegojobsearchapp.databinding.ActivityMainBinding
import com.example.kodegojobsearchapp.employer_viewpager.EmployerActivity
import com.example.kodegojobsearchapp.firebase.FirebaseUserDAOImpl
import com.example.kodegojobsearchapp.model.User
import com.example.kodegojobsearchapp.model.UserType
import com.example.kodegojobsearchapp.utils.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dao: FirebaseUserDAOImpl
    private lateinit var user: User
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth
        dao = FirebaseUserDAOImpl(applicationContext)
        progressDialog = ProgressDialog(binding.root.context, R.string.loading_user)
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }else{
            val firebaseUser = auth.currentUser!!
            progressDialog.show()
            lifecycleScope.launch {
                val firestoreUser = dao.getUser(firebaseUser.uid)
                if (firestoreUser == null){
                    user = User(firebaseUser)
                    dao.addUser(user)
                }else{
                    Log.d("UserType", firestoreUser.userType.name)
                    user = firestoreUser
                }
                progressDialog.dismiss()
                redirectUser()
            }
        }

        binding.btnJobseeker.setOnClickListener {
            val userTypeMap = hashMapOf<String, Any?>("userType" to UserType.APPLICANT)
            progressDialog.show()
            lifecycleScope.launch {
                dao.updateUser(userTypeMap)
                progressDialog.dismiss()
                redirectApplicant()
            }
        }

        binding.btnEmployer.setOnClickListener {
            val userTypeMap = hashMapOf<String, Any?>("userType" to UserType.EMPLOYER)
            progressDialog.show()
            lifecycleScope.launch {
                dao.updateUser(userTypeMap)
                progressDialog.dismiss()
                redirectEmployer()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
                bottomSheetDialogFragment.show(supportFragmentManager, "MyBottomSheetDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun redirectUser(){
        when (user.userType){
            UserType.APPLICANT -> redirectApplicant()
            UserType.EMPLOYER -> redirectEmployer()
            else -> {}
        }
    }

    private fun redirectApplicant(){
        val intent = Intent(this, ApplicantViewPagerActivity::class.java)
        val bundle = Bundle()
        bundle.putString("data","from_welcome_page")
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
    private fun redirectEmployer(){
        val intent = Intent(this, EmployerActivity::class.java)
        val bundle = Bundle()
        bundle.putString("data","from_welcome_page")
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}