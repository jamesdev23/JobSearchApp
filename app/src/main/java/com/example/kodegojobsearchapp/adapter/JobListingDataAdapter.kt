package com.example.kodegojobsearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.databinding.ItemJobListingBinding

class JobListingDataAdapter (private var context: Context,
                           private var jobListings : ArrayList<JobListingData>)
    : RecyclerView.Adapter<JobListingDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val itemBinding = ItemJobListingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = jobListings.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindItems(jobListings[position])
    }

    fun setList(newJobListings: ArrayList<JobListingData>) {
        this.jobListings.clear()
        this.jobListings.addAll(newJobListings)
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemJobListingBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(jobLists: JobListingData) {
            itemBinding.jobTitle.text = jobLists.jobTitle
            itemBinding.jobLocation.text = "${jobLists.jobCity}, ${jobLists.jobState}, ${jobLists.jobCountry}"
            itemBinding.jobCompany.text = jobLists.employerName
        }
    }
}
