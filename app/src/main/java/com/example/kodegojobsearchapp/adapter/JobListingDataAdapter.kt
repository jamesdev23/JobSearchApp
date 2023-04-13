package com.example.kodegojobsearchapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.JobDetailsActivity
import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.databinding.ItemJobListingBinding

class JobListingDataAdapter (private var context: Context, private var jobListings : ArrayList<JobListingData>, private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<JobListingDataAdapter.ViewHolder>() {

    fun setList(newJobListings: ArrayList<JobListingData>) {
        this.jobListings.clear()
        this.jobListings.addAll(newJobListings)
        notifyDataSetChanged()
    }

    override fun getItemCount() = jobListings.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemJobListingBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.bindItems(jobListings[position])

        holder.itemView.setOnClickListener{
            val jobID = jobListings[position].jobId
            val intent = Intent(context, JobDetailsActivity::class.java)

            val bundle = Bundle().apply {
                putString("job_id", jobID)
            }
            intent.putExtras(bundle)

            Log.d("job id from adapter", jobID)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(private val itemBinding: ItemJobListingBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItems(jobLists: JobListingData) {
            itemBinding.jobTitle.text = jobLists.jobTitle
            itemBinding.jobLocation.text = "${jobLists.jobCity}, ${jobLists.jobState}, ${jobLists.jobCountry}"
            itemBinding.jobCompany.text = jobLists.employerName
        }

        override fun onClick(view: View?) {
            // do nothing for now
        }
    }
}
