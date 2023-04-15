package com.example.kodegojobsearchapp.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor : Interceptor {
    //    private val API_KEY = ""
    private val API_KEY = "c855032a98mshcf0ec945a5fdd59p19ddbcjsnbf92c3e6d4f7"
    private val API_HOST = "jsearch.p.rapidapi.com"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()

        return chain.proceed(request)
    }
}