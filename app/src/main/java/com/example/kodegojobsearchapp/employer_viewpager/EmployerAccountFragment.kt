package com.example.kodegojobsearchapp.employer_viewpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kodegojobsearchapp.ChangeEmployerProfileActivity
import com.example.kodegojobsearchapp.ChangeProfilePictureActivity
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.SignInActivity
import com.example.kodegojobsearchapp.databinding.FragmentEmployerAccountBinding
import com.firebase.ui.auth.AuthUI

class EmployerAccountFragment : Fragment() {
    private var _binding: FragmentEmployerAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployerAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditProfilePicture.setOnClickListener {
            val intent = Intent(requireContext(), ChangeProfilePictureActivity::class.java)
            startActivity(intent)
        }
        binding.btnEditProfileDetails.setOnClickListener {
            val intent = Intent(requireContext(), ChangeEmployerProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()).addOnSuccessListener {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
        }
    }
}