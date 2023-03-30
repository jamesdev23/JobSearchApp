package com.example.kodegojobsearchapp.model

open class User() {
    var id: Int = 0
    var uID: String = "" /** Firebase User ID */
    var lastName: String = ""
    var firstName: String = ""
    var middleName: String = ""
    var email: String = ""
    var type: String = ""
    var image: String = ""
}