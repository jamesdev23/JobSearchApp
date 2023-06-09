package com.example.kodegojobsearchapp.api_model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * TODO: Proofread data inserted
 *  Check Git for changes in variable Type. Any can't be saved to database. Set as Strings?
 *  Add Ignored Fields to db?
 */
@Entity(tableName = "job_listing-table")
data class JobListingData(
    @SerializedName("employer_name")
    var employerName: String = "",

    @SerializedName("employer_logo")
    var employerLogo: String? = "",

    @SerializedName("employer_website")
    var employerWebsite: String? = "",

    @SerializedName("employer_company_type")
    var employerCompanyType: String? = "",

    @SerializedName("job_publisher")
    var jobPublisher: String = "",

    @PrimaryKey(false)
    @SerializedName("job_id")
    var jobId: String = "",

    @SerializedName("job_employment_type")
    var jobEmploymentType: String? = "",

    @SerializedName("job_title")
    var jobTitle: String = "",

    @SerializedName("job_apply_link")
    var jobApplyLink: String = "",

    @SerializedName("job_apply_is_direct")
    var jobApplyIsDirect: Boolean = false,

    @SerializedName("job_apply_quality_score")
    var jobApplyQualityScore: Double = 0.0,

    @SerializedName("job_description")
    var jobDescription: String = "",

    @SerializedName("job_is_remote")
    var jobIsRemote: Boolean = false,

    @SerializedName("job_posted_at_timestamp")
    var jobPostedAtTimestamp: Int = 0,

    @SerializedName("job_posted_at_datetime_utc")
    var jobPostedAtDatetimeUtc: String = "",

    @SerializedName("job_city")
    var jobCity: String? = "",

    @SerializedName("job_state")
    var jobState: String = "",

    @SerializedName("job_country")
    var jobCountry: String = "",

    @SerializedName("job_latitude")
    var job_latitude: Double = 0.0,

    @SerializedName("job_longitude")
    var job_longitude: Double = 0.0,

    @SerializedName("job_benefits")
    var job_benefits: String? = "",

    @SerializedName("job_google_link")
    var job_google_link: String = "",

    @SerializedName("job_offer_expiration_datetime_utc")
    var job_offer_expiration_datetime_utc: String? = "",

    @SerializedName("job_offer_expiration_timestamp")
    var job_offer_expiration_timestamp: Int = 0,

    @Ignore
    @SerializedName("job_required_experience")
    var job_required_experience: JobRequiredExperience = JobRequiredExperience(),

    @Ignore
    @SerializedName("job_required_skills")
    var job_required_skills: ArrayList<String> = ArrayList(),

    @Ignore
    @SerializedName("job_required_education")
    var job_required_education: JobRequiredEducation = JobRequiredEducation(),

    @SerializedName("job_experience_in_place_of_education")
    var job_experience_in_place_of_education: Boolean = false,

    @SerializedName("job_min_salary")
    var job_min_salary: String? = "",

    @SerializedName("job_max_salary")
    var job_max_salary: String? = "",

    @SerializedName("job_salary_currency")
    var job_salary_currency: String? = "",

    @SerializedName("job_salary_period")
    var job_salary_period: String? = "",

    @Ignore
    @SerializedName("job_highlights")
    var job_highlights: JobHighlights = JobHighlights(),

    @SerializedName("job_job_title")
    var job_job_title: String? = "",

    @SerializedName("job_posting_language")
    var job_posting_language: String = "",

    @SerializedName("job_onet_soc")
    var job_onet_soc: String = "",

    @SerializedName("job_onet_job_zone")
    var job_onet_job_zone: String = "",
)

class JobSearchResultResponse {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("request_id")
    var requestId: String = ""

    @SerializedName("parameters")
    var parameters: Parameters = Parameters()

    @SerializedName("data")
    var dataList: ArrayList<JobListingData> = ArrayList<JobListingData>()

    constructor(parameters: Parameters, dataList: ArrayList<JobListingData>){
        this.parameters = parameters
        this.dataList = dataList
    }
}

class Parameters {
    @SerializedName("query")
    var query: String = ""

    @SerializedName("page")
    var page: Int = 1

    @SerializedName("num_pages")
    var numPages: Int = 1
}

class JobRequiredExperience{
    @SerializedName("no_experience_required")
    var no_experience_required: Boolean = false

    @SerializedName("required_experience_in_months")
    var required_experience_in_months: Int = 0

    @SerializedName("experience_mentioned")
    var experience_mentioned: Boolean = false

    @SerializedName("experience_preferred")
    var experience_preferred: Boolean = false
}

class JobRequiredEducation{
    @SerializedName("postgraduate_degree")
    var postgraduate_degree: Boolean = false

    @SerializedName("professional_certification")
    var professional_certification: Boolean = false

    @SerializedName("high_school")
    var high_school: Boolean = false

    @SerializedName("associates_degree")
    var associates_degree: Boolean = false

    @SerializedName("bachelors_degree")
    var bachelors_degree: Boolean = false

    @SerializedName("degree_mentioned")
    var degree_mentioned: Boolean = false

    @SerializedName("degree_preferred")
    var degree_preferred: Boolean = false

    @SerializedName("professional_certification_mentioned")
    var professional_certification_mentioned: Boolean = false
}


class JobHighlights{
    @SerializedName("Qualifications")
    var qualifications: ArrayList<String> = ArrayList<String>()

    @SerializedName("Responsibilities")
    var responsibilities: ArrayList<String> = ArrayList<String>()
}




