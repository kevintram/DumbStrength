package com.kiwicorp.dumbstrength.data.source

import androidx.lifecycle.LiveData
import com.kiwicorp.dumbstrength.data.Activity
import com.kiwicorp.dumbstrength.data.source.local.ActivityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRepository @Inject constructor(private val activityDao: ActivityDao) {

    val activities = activityDao.observeActivities()

    suspend fun getSearchActivities(query: String?): List<Activity> {
        return withContext(Dispatchers.IO) {
            activityDao.getSearchedActivities(query)
        }
    }

    suspend fun getActivities(): List<Activity> {
        return withContext(Dispatchers.IO) {
            activityDao.getActivities()
        }
    }

    fun observeActivity(activityId: String): LiveData<Activity> {
        return activityDao.observeActivity(activityId)
    }

    suspend fun getActivity(activityId: String): Activity {
        return withContext(Dispatchers.IO) {
            activityDao.getActivity(activityId)
        }
    }

    suspend fun insertActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            activityDao.insertActivity(activity)
        }
    }

    suspend fun updateActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            activityDao.updateActivity(activity)
        }
    }

    suspend fun deleteActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            activityDao.deleteActivity(activity)
        }
    }
}