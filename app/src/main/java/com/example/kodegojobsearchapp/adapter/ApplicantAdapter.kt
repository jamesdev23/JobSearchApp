package com.example.kodegojobsearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.databinding.ItemApplicantBinding
import com.example.kodegojobsearchapp.model.JobSeeker
import com.google.android.material.snackbar.Snackbar

class ApplicantAdapter(var applicantList: ArrayList<JobSeeker>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemApplicantBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int  = applicantList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.itemBinding as ItemApplicantBinding
        val applicant = applicantList[position]
        with(binding){
            applicantName.text = applicant.name
            applicantTitle.text = applicant.title
            applicantRating.text = applicant.rating.toString()
            applicantDescription.text = applicant.description

            root.setOnClickListener {
                Snackbar.make(
                    it,
                    "${applicant.name}, ${applicant.title}, ${applicant.rating}, ${applicant.description}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}