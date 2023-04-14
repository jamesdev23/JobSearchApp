package com.example.kodegojobsearchapp.api_model

import com.google.gson.annotations.SerializedName

class UserListData {
    @SerializedName("id")
    var iD: Int = -1

    @SerializedName("email")
    var email = ""

    @SerializedName("first_name")
    var firstName = ""

    @SerializedName("last_name")
    var lastName = ""

    @SerializedName("avatar")
    var avatarUrl = ""

    constructor(firstname: String, lastname: String, url: String) {
        this.firstName = firstname
        this.lastName = lastname
        this.avatarUrl = url
    }
}

class UserListResponse {
    @SerializedName("page")
    var page: Int = -1

    @SerializedName("per_page")
    var perPage: Int = -1

    @SerializedName("total")
    var total: Int = -1

    @SerializedName("total_pages")
    var totalPages: Int = -1

    @SerializedName("data")
    var dataList: ArrayList<UserListData> = ArrayList<UserListData>()

    @SerializedName("support")
    var support: Support = Support()
}

class Support {
    @SerializedName("url")
    var support_url = ""

    @SerializedName("text")
    var support_text = ""
}