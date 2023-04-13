package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.JobDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobDetailsAPI {
    @GET("job-details")
    fun getJobInfo(
        @Query("job_id") jobId: String,
        @Query("extended_publisher_details") extendedPublisherDetails: Boolean):
            Call<JobDetailsResponse>
}