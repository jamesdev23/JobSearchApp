package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Company(): Parcelable {
    var companyID: String = ""
    var name: String = ""
    var address: String = ""
    var telephone: String = ""
    var website: String = ""
}