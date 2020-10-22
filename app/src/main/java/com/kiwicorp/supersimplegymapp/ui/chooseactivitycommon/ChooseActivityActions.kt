package com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon

import com.kiwicorp.supersimplegymapp.data.Activity

interface ChooseActivityActions {
    fun chooseActivity(activity: Activity)
    fun unchooseActivity(activity: Activity)
    fun activityIsInEntries(activity: Activity): Boolean
}