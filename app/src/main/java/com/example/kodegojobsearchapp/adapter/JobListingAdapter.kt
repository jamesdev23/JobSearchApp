package com.example.kodegojobsearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.databinding.ItemJobListingBinding
import com.example.kodegojobsearchapp.model.JobListing
import com.google.android.material.snackbar.Snackbar

class JobListingAdapter(var jobsListing: ArrayList<JobListing>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemJobListingBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int  = jobsListing.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.itemBinding as ItemJobListingBinding
        val jobList = jobsListing[position]
        with(binding){
            jobTitle.text = jobList.title
            jobLocation.text = jobList.title
            jobDescription.text = jobList.description

            root.setOnClickListener {
                Snackbar.make(
                    it,
                    "${jobList.title}, ${jobList.location}, ${jobList.description}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}