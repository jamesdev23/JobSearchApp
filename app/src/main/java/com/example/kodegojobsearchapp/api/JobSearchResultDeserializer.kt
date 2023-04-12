package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.JobListingData
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.api_model.Parameters
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class JobSearchResultDeserializer : JsonDeserializer<JobSearchResultResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): JobSearchResultResponse {

        val gson = Gson()
        val jsonObject = json?.asJsonObject
        val dataObject = jsonObject?.getAsJsonObject("data")

        val results = gson.fromJson<ArrayList<JobListingData>>(
            dataObject?.getAsJsonArray("results"),
            object : TypeToken<ArrayList<JobListingData>>() {}.type
        )

        val metadata = gson.fromJson<Parameters>(
            dataObject?.getAsJsonArray("metadata"),
            object : TypeToken<Parameters>() {}.type
        )

        return JobSearchResultResponse(metadata, results)
    }
}