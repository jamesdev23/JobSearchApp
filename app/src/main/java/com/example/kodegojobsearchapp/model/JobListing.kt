package com.example.kodegojobsearchapp.model

data class JobListing(val title: String, val location: String, val description: String){
    constructor(): this("","","")
}