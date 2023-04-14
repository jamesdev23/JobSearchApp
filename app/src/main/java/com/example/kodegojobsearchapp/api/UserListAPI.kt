package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.JobSearchResultResponse
import com.example.kodegojobsearchapp.api_model.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserListAPI {
    @GET("users")
    fun getUserList(
        @Query("page") startIndex:Int
    ): Call<UserListResponse>
}