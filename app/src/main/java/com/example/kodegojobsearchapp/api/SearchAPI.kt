package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchAPI {
    @GET("search")
    fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("num_pages") numPages: Int,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): Call<JobSearchResultResponse>
}