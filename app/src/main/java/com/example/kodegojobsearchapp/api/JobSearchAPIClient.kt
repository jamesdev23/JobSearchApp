package com.example.kodegojobsearchapp.api

import android.util.Log
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

    val getJobSearchData: JobSearchAPI
        get() {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(CustomInterceptor())
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            Log.d("gson", gson.toString())

            return retrofit.create(JobSearchAPI::class.java)
        }

    val getJobDetailsData: JobDetailsAPI
        get() {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(CustomInterceptor())
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            Log.d("gson", gson.toString())

            return retrofit.create(JobDetailsAPI::class.java)
        }
}

class CustomInterceptor : Interceptor {
    private val API_KEY = "b160f8de0fmsh9bf806435608629p1f65b0jsnea1fc1fbc488"
    private val API_HOST = "jsearch.p.rapidapi.com"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()

        return chain.proceed(request)
    }
}