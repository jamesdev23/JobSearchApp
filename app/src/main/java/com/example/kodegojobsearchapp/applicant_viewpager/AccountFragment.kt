package com.example.kodegojobsearchapp.applicant_viewpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kodegojobsearchapp.SignInActivity
import com.example.kodegojobsearchapp.databinding.FragmentAccountBinding
import com.example.kodegojobsearchapp.ChangeProfileDetailsActivity
import com.example.kodegojobsearchapp.ChangeProfilePictureActivity
import com.firebase.ui.auth.AuthUI

class AccountFragment : Fragment() {
    private lateinit var _binding: FragmentAccountBinding
    private val binding get() = _binding
    init {
        if(this.arguments == null) {
            getTabInfo()
        }
    }
    private fun getTabInfo(){
        this.arguments = Bundle().apply {
            putString(FragmentKeys.TabName, "Account")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container,false)

        binding.btnEditProfilePicture.setOnClickListener {
            val intent = Intent(requireContext(), ChangeProfilePictureActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditProfileDetails.setOnClickListener {
            val intent = Intent(requireContext(), ChangeProfileDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()).addOnSuccessListener {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
        }


        return binding.root
    }


}