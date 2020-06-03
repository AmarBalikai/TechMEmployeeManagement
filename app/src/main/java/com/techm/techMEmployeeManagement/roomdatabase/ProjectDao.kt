package com.techm.techMEmployeeManagement.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * In this interface have all queries fo room database
 */
@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAllProject(): LiveData<List<ModelProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ModelProject): Long

}