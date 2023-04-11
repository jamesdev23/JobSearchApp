package com.example.kodegojobsearchapp.model

import com.google.gson.annotations.SerializedName

data class JobListing(val title: String, val location: String, val description: String){
    constructor(): this("","","")
}

data class IndeedJobListing(
    @SerializedName("jobtitle")
    val jobTitle: String = "",
    val company: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val formattedLocation: String = "",
    val source: String = "",
    val date: String = "",
    val snippet: String = "",
    val url: String = "",
    @SerializedName("onmousedown")
    val onMouseDown: String = "",
    @SerializedName("jobkey")
    val jobKey: String = "",
    val sponsored: Boolean = false,
    val expired: Boolean = false,
    val formattedLocationFull: String = "",
    val formattedRelativeTime: String = ""
)

data class IndeedJobListResponse( //TODO: Implement API
    val version: Int = 0,
    val query: String = "",
    val location: String = "",
    @SerializedName("dupefilter")
    val dupeFilter: Boolean = false,
    val highlight: Boolean = false,
    val radius: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val totalResults: Int = 0,
    val pageNumber: Int = 0
){
    @SerializedName("results")
    val jobList: ArrayList<IndeedJobListing> = ArrayList()
}