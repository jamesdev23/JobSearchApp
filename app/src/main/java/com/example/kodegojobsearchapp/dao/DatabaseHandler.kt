package com.example.kodegojobsearchapp.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {

    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "jobsearchdatabase"

        // user table

        val tableUser = "user_table"
        val userID = "id"
        val userLastName = "last_name"
        val userFirstName = "first_name"
        val userMiddleName = "middle_name"
        val userEmail = "email"
        val userType = "type"
        val userImageText = "image_text"


    }

    override fun onCreate(db: SQLiteDatabase?) {

        // creating student table

        val CREATEUSERSTABLE = "CREATE TABLE $tableUser " +
                "($userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$userLastName TEXT," +
                "$userFirstName TEXT, " +
                "$userMiddleName TEXT," +
                "$userType TEXT," +
                "$userImageText TEXT)"

        db?.execSQL(CREATEUSERSTABLE)

        db?.execSQL("Insert into $tableUser ($userLastName, $userFirstName, $userMiddleName, $userEmail, $userType, $userImageText) values ('Smith', 'John', 'Adam', 'johnsmith@johnsmith.com.us', 'applicant', 'sample image')")


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableUser")
        onCreate(db)
    }
}

