package com.example.kodegojobsearchapp.api

import com.example.kodegojobsearchapp.api_model.UserListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserListAPI {
    @GET("users")
    fun getUserList(
        @Query("size") size: Int,
        @Query("response_type") responseType: String
    ): Call<List<UserListData>>
}