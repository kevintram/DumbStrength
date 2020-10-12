package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kiwicorp.supersimplegymapp.data.Activity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    fun observeActivities(): LiveData<List<Activity>>

    @Query("SELECT * FROM activities")
    suspend fun getActivities(): List<Activity>

    @Insert
    suspend fun insertActivity(activity: Activity)
}