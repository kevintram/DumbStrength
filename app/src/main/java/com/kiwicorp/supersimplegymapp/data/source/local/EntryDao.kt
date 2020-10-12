package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import com.kiwicorp.supersimplegymapp.data.Entry

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(entry: Entry)
}