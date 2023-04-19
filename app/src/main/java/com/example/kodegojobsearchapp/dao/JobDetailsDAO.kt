package com.example.kodegojobsearchapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kodegojobsearchapp.api_model.JobDetailsData

@Dao
interface JobDetailsDAO {
    @Insert
    suspend fun insert(jobDetails: JobDetailsData)

    @Update
    suspend fun update(jobDetails: JobDetailsData): Int

    @Delete
    suspend fun delete(jobDetails: JobDetailsData): Int

    @Query("DELETE FROM `job_details-table`")
    suspend fun clean()

    @Query("SELECT * FROM `job_details-table`")
    suspend fun getJobDetailsList():List<JobDetailsData>

    @Query("SELECT * FROM `job_details-table` WHERE jobId = :jobID")
    suspend fun getJobDetails(jobID: String): JobDetailsData?
}