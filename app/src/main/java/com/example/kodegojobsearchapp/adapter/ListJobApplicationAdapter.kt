package com.example.kodegojobsearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.databinding.ItemJobApplicationBinding
import com.example.kodegojobsearchapp.model.JobApplication
import com.example.kodegojobsearchapp.utils.bind

class ListJobApplicationAdapter(private val jobApplications: ArrayList<JobApplication>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemJobApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = jobApplications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.itemBinding as ItemJobApplicationBinding
        val application = jobApplications[position]
        binding.bind(application)
    }
}