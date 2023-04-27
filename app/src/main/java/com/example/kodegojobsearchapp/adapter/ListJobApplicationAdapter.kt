package com.example.kodegojobsearchapp.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobDetailsResponse
import com.example.kodegojobsearchapp.databinding.ItemJobApplicationBinding
import com.example.kodegojobsearchapp.model.JobApplication
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListJobApplicationAdapter(private val jobApplications: ArrayList<JobApplication>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemJobApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = jobApplications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val binding = holder.itemBinding as ItemJobApplicationBinding
        val application = jobApplications[position]
        getData(application, holder)
    }

    private fun getData(application: JobApplication, holder: ViewHolder){
        val binding = holder.itemBinding as ItemJobApplicationBinding
        val call: Call<JobDetailsResponse> = JobSearchAPIClient
            .getJobDetailsData.getJobDetails(application.jobID, false)

        call.enqueue(object : Callback<JobDetailsResponse> {
            override fun onFailure(call: Call<JobDetailsResponse>, t: Throwable) {
                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
                removeItem(application, holder)
            }

            override fun onResponse(
                call: Call<JobDetailsResponse>,
                response: Response<JobDetailsResponse>
            ) {
                if (response.isSuccessful && response.body()!!.data.isNotEmpty()) {
                    val response: JobDetailsResponse = response.body()!!

                    val data = response.data[0]
                    loadCompanyLogo(binding.companyLogo, data.employerLogo.toString())
                    binding.jobTitle.text = data.jobTitle
                    binding.jobCompany.text = data.employerName
                }else{
                    Log.e("JobDetail Call", response.message())
                    removeItem(application, holder)
                }
            }
        })
    }

    private fun removeItem(application: JobApplication, holder: ViewHolder){
//        jobApplications.remove(application)
        val index = jobApplications.indexOfFirst { it.jobID == application.jobID }
        jobApplications.removeAt(index)
        notifyItemRemoved(holder.bindingAdapterPosition)
    }

    private fun loadCompanyLogo(imageView: ImageView, image: String){
        Picasso
            .with(imageView.context)
            .load(image)
//            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .placeholder(R.drawable.company_logo_placeholder)
            .error(R.drawable.company_logo_placeholder)
            .into(imageView)
    }
}