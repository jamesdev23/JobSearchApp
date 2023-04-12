package com.example.kodegojobsearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.databinding.ItemJobListingBinding
import com.google.android.material.snackbar.Snackbar

class JobListingDataAdapter(var jobListingsData: MutableList<JobListingData>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemJobListingBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int  = jobListingsData.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.itemBinding as ItemJobListingBinding
        val jobList = jobListingsData[position]
        val jobListLocation =  "{${jobList.jobCity}, ${jobList.jobState}, ${jobList.jobCountry}"

        with(binding){
            jobTitle.text = jobList.jobTitle
            jobLocation.text = jobListLocation
            jobDescription.text = jobList.jobDescription

            root.setOnClickListener {
                Snackbar.make(
                    it,
                    "${jobList.jobTitle}, ${jobListLocation}, ${jobList.jobDescription}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun setJobListing(newJobListings: MutableList<JobListingData>){
        this.jobListingsData.clear()
        this.jobListingsData.addAll(newJobListings)
        notifyDataSetChanged()
    }

}