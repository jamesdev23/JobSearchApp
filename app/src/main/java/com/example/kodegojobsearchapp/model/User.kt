package com.example.kodegojobsearchapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize // TODO: Use Parcelable implementation instead if Subclass will also be Parceled
open class User(): Parcelable {
    var id: Int = 0
    var uID: String = "" /** Firebase User ID */
    var firstName: String = ""
    var lastName: String = ""
    var middleName: String = ""
    var email: String = ""
    var type: String = ""
    var image: String = ""

    var isEmployer: Boolean = false

    fun setUser(user: User){
        id = user.id
        uID = user.uID
        firstName = user.firstName
        lastName = user.lastName
        middleName = user.middleName
        email = user.email
        type = user.type
        image = user.image
    }
}

