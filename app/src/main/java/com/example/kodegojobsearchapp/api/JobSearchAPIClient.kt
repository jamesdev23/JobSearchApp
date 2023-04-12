package com.example.kodegojobsearchapp.api

import android.util.Log
import com.example.kodegojobsearchapp.api.JobSearchAPIClient.API_HOST
import com.example.kodegojobsearchapp.api.JobSearchAPIClient.API_KEY
import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JobSearchAPIClient {
    private const val BASE_URL = "https://jsearch.p.rapidapi.com/"
    const val API_KEY = "b160f8de0fmsh9bf806435608629p1f65b0jsnea1fc1fbc488"
    const val API_HOST = "jsearch.p.rapidapi.com"

    val getJobSearchData: JobSearchAPI
        get() {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(CustomInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .setLenient()
                            .registerTypeAdapter(JobSearchResultResponse::class.java, JobSearchResultDeserializer())
                            .create()
                    )
                )
                .client(client)
                .build()

            Log.d("gson", gson.toString())

            return retrofit.create(JobSearchAPI::class.java)
        }
}

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", JobSearchAPIClient.API_KEY)  // don't change this one. it is intentional for direct var access
            .addHeader("X-RapidAPI-Host", JobSearchAPIClient.API_HOST) // don't change this one. it is intentional for direct var access
            .build()

        return chain.proceed(request)
    }
}