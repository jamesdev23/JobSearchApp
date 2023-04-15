package com.example.kodegojobsearchapp.api_model

import com.google.gson.annotations.SerializedName

data class UserListData (
    @SerializedName("id")
    val id: Int,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatar")
    val avatar: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("social_insurance_number")
    val socialInsuranceNumber: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: String,

    @SerializedName("employment")
    val employment: Employment,

    @SerializedName("address")
    val address: Address,

    @SerializedName("credit_card")
    val creditCard: CreditCard,

    @SerializedName("subscription")
    val subscription: Subscription
)
data class Employment(
    @SerializedName("title")
    val title: String,

    @SerializedName("key_skill")
    val keySkill: String
)

data class Address(
    @SerializedName("city")
    val city: String,

    @SerializedName("street_name")
    val streetName: String,

    @SerializedName("street_address")
    val streetAddress: String,

    @SerializedName("zip_code")
    val zipCode: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("coordinates")
    val coordinates: Coordinates
)

data class Coordinates(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)

data class CreditCard(
    @SerializedName("cc_number")
    val ccNumber: String
)

data class Subscription(
    @SerializedName("plan")
    val plan: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("term")
    val term: String
)
