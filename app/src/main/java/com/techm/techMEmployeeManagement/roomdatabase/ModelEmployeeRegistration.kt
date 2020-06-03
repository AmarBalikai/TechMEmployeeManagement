package com.techm.techMEmployeeManagement.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class for register employee
 * */

@Entity(tableName = "employee_information")
class ModelEmployeeRegistration(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var band: String,
    var designation: String,
    var employeeId: String,
    var competency: String,
    var project:String
)