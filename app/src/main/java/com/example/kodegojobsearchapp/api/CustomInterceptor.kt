package com.example.kodegojobsearchapp.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor : Interceptor {
    //    private val API_KEY = ""
    private val API_KEY = "b958ea6718mshfa77b9027b96fabp176892jsn7b18b73e6425"
    private val API_HOST = "jsearch.p.rapidapi.com"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()

        return chain.proceed(request)
    }
}