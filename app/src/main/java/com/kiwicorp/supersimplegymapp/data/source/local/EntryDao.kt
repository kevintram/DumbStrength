package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kiwicorp.supersimplegymapp.data.Entry
import com.kiwicorp.supersimplegymapp.data.EntryWithActivity

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(entry: Entry)

    @Query("SELECT * FROM entries WHERE entry_activity_id = :activityId")
    fun observeEntriesByActivityId(activityId: String): LiveData<List<Entry>>
}