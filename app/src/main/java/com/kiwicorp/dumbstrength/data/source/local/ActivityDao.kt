package com.kiwicorp.dumbstrength.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiwicorp.dumbstrength.data.Activity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities ORDER BY activity_name COLLATE NOCASE")
    fun observeActivities(): LiveData<List<Activity>>

    @Query("SELECT * FROM activities ORDER BY activity_name COLLATE NOCASE")
    suspend fun getActivities(): List<Activity>

    @Query("SELECT * FROM activities WHERE activity_name LIKE '%' || :query || '%' ORDER BY activity_name COLLATE NOCASE")
    suspend fun getSearchedActivities(query: String?): List<Activity>

    @Query("SELECT * FROM activities WHERE activity_id = :activityId")
    fun observeActivity(activityId: String): LiveData<Activity>

    @Query("SELECT * FROM activities WHERE activity_id = :activityId")
    suspend fun getActivity(activityId: String): Activity

    @Insert
    suspend fun insertActivity(activity: Activity)

    @Update
    suspend fun updateActivity(activity: Activity): Int

    @Delete
    suspend fun deleteActivity(activity: Activity): Int
}