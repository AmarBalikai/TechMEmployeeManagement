package com.techm.techMEmployeeManagement

import androidx.lifecycle.LiveData
import androidx.room.*

import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration

/**
 * In this interface have all queries fo room database
 */
@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee_information")
    fun getAllEmployee(): LiveData<List<ModelEmployeeRegistration>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: ModelEmployeeRegistration): Long

    @Insert
    suspend fun insertAll(employeeList: ArrayList<ModelEmployeeRegistration>?)

    @Delete
    suspend fun delete(employee: ModelEmployeeRegistration): Int

    @Query("DELETE FROM employee_information")
    suspend fun deleteAllEmployee()

    @Update
    suspend fun updateEmployee( todo: ModelEmployeeRegistration): Int

    @Query("SELECT * FROM employee_information WHERE id = :id")
    suspend fun findByEmployeeId(id: String?): ModelEmployeeRegistration
}