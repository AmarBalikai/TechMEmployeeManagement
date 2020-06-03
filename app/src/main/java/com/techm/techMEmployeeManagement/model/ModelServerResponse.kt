package com.techm.techMEmployeeManagement.model

import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.utils.ResponseStatus


/**
 * This class is for handle server response
 * */
class ModelServerResponse(var data:ArrayList<ModelEmployeeRegistration> ,var errorMessage:String="",var status: ResponseStatus)