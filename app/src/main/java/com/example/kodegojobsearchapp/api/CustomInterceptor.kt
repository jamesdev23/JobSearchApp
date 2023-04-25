package com.example.kodegojobsearchapp.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor : Interceptor {
    //    private val API_KEY = ""
    private val API_KEY = "a255ef7b41msh9fc271ec6bfdc5bp1dcadejsn0764b064eee7"
    private val API_HOST = "jsearch.p.rapidapi.com"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()

        return chain.proceed(request)
    }
}