package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface JobSearchAPI {
    @GET("search")
    fun getJobData(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("num_pages") numPages: Int
    ): Call<JobSearchResultResponse>
}