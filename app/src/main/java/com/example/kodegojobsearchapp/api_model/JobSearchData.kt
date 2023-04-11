package com.example.kodegojobsearchapp.api_model

import com.google.gson.annotations.SerializedName

class JobSearchResultResponse {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("request_id")
    var request_id: String = ""

    @SerializedName("parameters")
    var parameters: Parameter = Parameter()

    @SerializedName("data")
    var dataList: ArrayList<JobData> = ArrayList<JobData>()
}

class Parameter {
    @SerializedName("query")
    var query: String = ""

    @SerializedName("page")
    var page: Int = -1

    @SerializedName("num_pages")
    var num_pages: Int = -1
}

class JobData {
    @SerializedName("employer_name")
    var employer_name: String = ""

    @SerializedName("employer_logo")
    var employer_logo: String = ""

    @SerializedName("job_publisher")
    var job_publisher: String = ""

    @SerializedName("job_id")
    var job_id: String = ""

    @SerializedName("job_employment_type")
    var job_employment_type: String = ""

    @SerializedName("job_title")
    var job_title: String = ""

    @SerializedName("job_apply_link")
    var job_apply_link: String = ""

    @SerializedName("job_apply_is_direct")
    var job_apply_is_direct: Boolean = false

    @SerializedName("job_apply_quality_score")
    var job_apply_quality_score: Int = -1

    @SerializedName("job_description")
    var job_description: String = ""

    @SerializedName("job_is_remote")
    var job_is_remote: Boolean = false

    @SerializedName("job_posted_at_timestamp")
    var job_posted_at_timestamp: Int = -1

    @SerializedName("job_posted_at_datetime_utc")
    var job_posted_at_datetime_utc: String = ""

    @SerializedName("job_city")
    var job_city: String = ""

    @SerializedName("job_state")
    var job_state: String = ""

    @SerializedName("job_latitude")
    var job_latitude: Double = -1.0

    @SerializedName("job_longitude")
    var job_longitude: Double = -1.0

    @SerializedName("job_google_link")
    var job_google_link: String = ""

    @SerializedName("job_required_experience")
    var job_required_experience: JobRequiredExperience = JobRequiredExperience()

    @SerializedName("job_required_skills")
    var job_required_skills: ArrayList<String> = ArrayList()

    @SerializedName("job_required_education")
    var job_required_education: JobRequiredEducation = JobRequiredEducation()

    @SerializedName("job_experience_in_place_of_education")
    var job_experience_in_place_of_education: Boolean = false

    @SerializedName("job_min_salary")
    var job_min_salary: Int = -1

    @SerializedName("job_max_salary")
    var job_max_salary: Int = -1

    @SerializedName("job_salary_currency")
    var job_salary_currency: String = ""

    @SerializedName("job_salary_period")
    var job_salary_period: String = ""

    @SerializedName("job_highlights")
    var job_highlights: JobHighlights = JobHighlights()

    @SerializedName("job_posting_language")
    var job_posting_language: String = ""

    @SerializedName("job_onet_soc")
    var job_onet_soc: String = ""

    @SerializedName("job_onet_job_zone")
    var job_onet_job_zone: String = ""

}

class JobHighlights{
    @SerializedName("Qualifications")
    var qualifications: ArrayList<String> = ArrayList()

    @SerializedName("Responsibilities")
    var responsibilities: ArrayList<String> = ArrayList()
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

class JobRequiredExperience{
    @SerializedName("no_experience_required")
    var no_experience_required: Boolean = false

    @SerializedName("required_experience_in_months")
    var job_is_required_experience_in_monthsremote: Int = -1

    @SerializedName("required_experience_in_months")
    var required_experience_in_months: Boolean = false

    @SerializedName("experience_preferred")
    var experience_preferred: Boolean = false
}