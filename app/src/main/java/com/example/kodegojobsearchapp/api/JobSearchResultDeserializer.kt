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

        val jsonObject = json?.asJsonObject
        val dataObject = jsonObject?.getAsJsonObject("data")

        val results = mutableListOf<JobListingData>()
        val jobSearchResultType = object : TypeToken<List<JobListingData>>() {}.type
        dataObject?.getAsJsonArray("results")?.let { jsonArray ->
            results.addAll(Gson().fromJson(jsonArray, jobSearchResultType))
        }

        val jobSearchMetadataType = object : TypeToken<Parameters>() {}.type
        val metadata = Gson().fromJson(dataObject?.getAsJsonObject("metadata"), jobSearchMetadataType) as Parameters

        return JobSearchResultResponse(results,metadata)
    }
}