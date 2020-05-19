package com.techm.techMEmployeeManagement.network

import com.techm.techMEmployeeManagement.model.ModelDeleteEmployee
import com.techm.techMEmployeeManagement.model.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.model.ModelEmployeeServerResponse
import com.techm.techMEmployeeManagement.model.ModelServerResponse
import com.techm.techMEmployeeManagement.utils.Constant
import com.techm.techMEmployeeManagement.utils.Constant.Companion.deleteEmployeeApi
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    /**
     * This method is getting for list's of objects from server
     */
    @GET(Constant.employeeInformationUrl)
    fun getEmployeeInformation(): Call<ModelServerResponse>

    /**
     * This method for insert employee
     * */
    @POST(Constant.insertEmployeeApi)
    fun insertEmployee(@Body body: ModelEmployeeRegistration): Call<ModelEmployeeServerResponse>

    /**
     * This method for delete employee
     * */
    @DELETE(deleteEmployeeApi)
    fun deleteEmployee(@Path("id") id: String): Call<ModelDeleteEmployee>
}