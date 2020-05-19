package com.techm.techMEmployeeManagement.model

import com.techm.techMEmployeeManagement.utils.ResponseStatus


/**
 * This class for handle API response
 * */
class ModelEmployeeServerResponse(
    var error: String,
    var status: ResponseStatus,
    var data: ModelEmployeeRegistration
)