package com.example.kodegojobsearchapp.model

import com.google.gson.annotations.SerializedName

class JobListing {
    var jobListingID: String = ""
    var title: String = ""
    var location: String = ""
    var description: String = ""

    constructor(title: String, location: String, description: String)
}