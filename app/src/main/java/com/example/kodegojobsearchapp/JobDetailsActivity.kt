package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kodegojobsearchapp.api.JobSearchAPIClient
import com.example.kodegojobsearchapp.api_model.JobDetailsData
import com.example.kodegojobsearchapp.api_model.JobDetailsResponse
import com.example.kodegojobsearchapp.databinding.ActivityJobDetailsBinding
import com.example.kodegojobsearchapp.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobDetailsBinding
    
    private lateinit var jobDetailsList: ArrayList<JobDetailsData>
    private lateinit var jobDetailsData: JobDetailsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val jobID = bundle?.getString("job_id")
        
        supportActionBar?.apply{
            title = "Job Details"
            setDisplayHomeAsUpEnabled(true)
        }

        getData(jobID!!)

        binding.btnApply.setOnClickListener{
            /**
             * TODO: (for dave) add code when applicant clicks the apply button
             *  Supposedly 1 JobDetailsData
             *      Save Job and Applicant IDs on JobApplication Model
             *      Store JobApplication on Firestore
             *
             */




        }
        
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    
    private fun getData(jobId: String){
        binding.scrollJobDetails.visibility = View.GONE
        binding.btnApply.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE

        val call: Call<JobDetailsResponse> = JobSearchAPIClient
            .getJobDetailsData.getJobDetails(jobId, false)

        call.enqueue(object : Callback<JobDetailsResponse> {
            override fun onFailure(call: Call<JobDetailsResponse>, t: Throwable) {
                binding.scrollJobDetails.visibility = View.VISIBLE
                binding.btnApply.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE

                Log.d("API CALL", "Failed API CALL")
                Log.e("error", t.message.toString())
            }

            override fun onResponse(
                call: Call<JobDetailsResponse>,
                response: Response<JobDetailsResponse>
            ) {
                binding.scrollJobDetails.visibility = View.VISIBLE
                binding.btnApply.visibility = View.VISIBLE
                binding.loadingData.visibility = View.GONE

                var response: JobDetailsResponse = response!!.body()!!

                jobDetailsList = response.data

                for(jobDetails in jobDetailsList){
                    binding.jobTitle.text = jobDetails.jobTitle
                    binding.jobCompany.text = jobDetails.employerName
                    binding.jobLocation.text = "${jobDetails.jobCity}, ${jobDetails.jobState}, ${jobDetails.jobCountry}"
                    binding.jobPostedTime.text = jobDetails.jobPostedAtDatetimeUtc
                    binding.jobEmploymentType.text = jobDetails.jobEmploymentType
                    binding.jobPublisher.text = jobDetails.jobPublisher
                    binding.jobDescription.text = jobDetails.jobDescription

                    Log.d("API CALL", "${jobDetails.jobTitle} ${jobDetails.employerName}")
                }
            }
        })
    }
}