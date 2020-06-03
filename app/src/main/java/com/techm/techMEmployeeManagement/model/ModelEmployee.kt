package com.techm.techMEmployeeManagement.model

import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.utils.ResponseStatus


/**
 * This class for handle API response
 * */
class ModelEmployee(
    var error: String,
    var status: ResponseStatus
)