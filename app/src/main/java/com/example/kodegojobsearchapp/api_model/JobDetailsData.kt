package com.example.kodegojobsearchapp.api_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "job_details-table")
class JobDetailsData{
    @SerializedName("employer_name")
    var employerName: String = ""

    @SerializedName("employer_logo")
    var employerLogo: String? = ""

    @SerializedName("employer_website")
    var employerWebsite: String? = ""

    @SerializedName("employer_company_type")
    var employerCompanyType: String? = ""

    @SerializedName("job_publisher")
    var jobPublisher: String = ""

    @PrimaryKey(false)
    @SerializedName("job_id")
    var jobId: String = ""

    @SerializedName("job_employment_type")
    var jobEmploymentType: String = ""

    @SerializedName("job_title")
    var jobTitle: String = ""

    @SerializedName("job_apply_link")
    var jobApplyLink: String = ""

    @SerializedName("job_apply_is_direct")
    var jobApplyIsDirect: Boolean = false

    @SerializedName("job_apply_quality_score")
    var jobApplyQualityScore: Double = 0.0

    @SerializedName("job_description")
    var jobDescription: String = ""

    @SerializedName("job_is_remote")
    var jobIsRemote: Boolean = false

    @SerializedName("job_posted_at_timestamp")
    var jobPostedAtTimestamp: Int = 0

    @SerializedName("job_posted_at_datetime_utc")
    var jobPostedAtDatetimeUtc: String = ""

    @SerializedName("job_city")
    var jobCity: String? = ""

    @SerializedName("job_state")
    var jobState: String  = ""

    @SerializedName("job_country")
    var jobCountry: String  = ""

    @SerializedName("job_latitude")
    var jobLatitude: Double = 0.0

    @SerializedName("job_longitude")
    var jobLongitude: Double = 0.0

    // job benefits goes here

    @SerializedName("job_google_link")
    var jobGoogleLink: String = ""

    fun location(): String = "$jobCity, $jobState, $jobCountry"
}

class JobDetailsResponse{
    @SerializedName("status")
    val status: String = ""

    @SerializedName("request_id")
    val requestId: String = ""

    @SerializedName("parameters")
    val parameters: JobDetailsParameters = JobDetailsParameters()

    @SerializedName("data")
    val data: ArrayList<JobDetailsData> = arrayListOf()
}

class JobDetailsParameters{
    @SerializedName("job_id")
    val jobId: String  = ""

    @SerializedName("extended_publisher_details")
    val extendedPublisherDetails: Boolean = false
}