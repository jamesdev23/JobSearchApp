package com.example.kodegojobsearchapp.model

import com.google.gson.annotations.SerializedName

data class JobListing(val title: String, val location: String, val description: String){
    constructor(): this("","","")
}