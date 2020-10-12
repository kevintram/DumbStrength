package com.kiwicorp.supersimplegymapp.data.source

import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.source.local.ActivityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRepository @Inject constructor(private val activityDao: ActivityDao) {
    val activities = activityDao.observeActivities()

    suspend fun insertActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            activityDao.insertActivity(activity)
        }
    }
}