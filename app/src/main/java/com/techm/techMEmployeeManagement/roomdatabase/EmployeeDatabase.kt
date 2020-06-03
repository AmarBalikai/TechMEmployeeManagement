package com.techm.telstra.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techm.techMEmployeeManagement.EmployeeDao
import com.techm.techMEmployeeManagement.model.ModelEmployee
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import com.techm.techMEmployeeManagement.roomdatabase.ProjectDao

/**
 * This class is creating for room database
 */
@Database(entities = [ModelEmployeeRegistration::class, ModelProject::class], version = 2)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun getEmployeeDao(): EmployeeDao
    abstract fun getProjectDao(): ProjectDao
    companion object {
        @Volatile
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java, "employee_database.db"
        )
            .fallbackToDestructiveMigration()//we need it when we increase version number
            .build()
    }
}