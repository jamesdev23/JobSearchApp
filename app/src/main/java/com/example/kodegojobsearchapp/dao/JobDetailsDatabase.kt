package com.example.kodegojobsearchapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kodegojobsearchapp.api_model.JobDetailsData

@Database(entities = [JobDetailsData::class], version = 4)
abstract class JobDetailsDatabase: RoomDatabase() {
    abstract fun jobDetailsDAO(): JobDetailsDAO

    companion object{
        @Volatile
        private var INSTANCE: JobDetailsDatabase? = null

        fun getInstance(context: Context): JobDetailsDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JobDetailsDatabase::class.java,
                        "job_details-database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}