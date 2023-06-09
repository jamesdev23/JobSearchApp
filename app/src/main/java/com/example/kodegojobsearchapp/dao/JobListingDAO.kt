package com.example.kodegojobsearchapp.dao

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.kodegojobsearchapp.api_model.JobListingData

@Dao
interface JobListingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobListing: JobListingData)

    @Update
    suspend fun update(jobListing: JobListingData): Int

    @Delete
    suspend fun delete(jobListing: JobListingData): Int

    @Query("DELETE FROM `job_listing-table`")
    suspend fun clean()

    @Query("SELECT * FROM `job_listing-table`")
    suspend fun getJobLists():List<JobListingData>

    @Query("SELECT * FROM `job_listing-table` LIMIT :offset, 10")
    suspend fun getJobLists(offset: Int): List<JobListingData>

    @Query("SELECT * FROM `job_listing-table` WHERE jobId = :jobID")
    suspend fun getJobListing(jobID: String) : JobListingData?
}

@Database(entities = [JobListingData::class], version = 5)
abstract class JobListingDatabase: RoomDatabase(){
    abstract fun jobListingDAO(): JobListingDAO

    companion object{
        @Volatile
        private var INSTANCE: JobListingDatabase? = null

        fun getInstance(context: Context): JobListingDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JobListingDatabase::class.java,
                        "job_listing-table"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}