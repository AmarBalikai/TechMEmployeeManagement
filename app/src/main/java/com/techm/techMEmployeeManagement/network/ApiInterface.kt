package com.techm.techMEmployeeManagement.network

import com.techm.techMEmployeeManagement.utils.Constant
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
    @POST(Constant.insertEmployee)
    fun insertEmployee(@Body body: ModelEmployeeRegistration): Call<ModelEmployeeServerResponse>

    /**
     * This method for delete employee
     * */
    @DELETE(deleteEmployee)
    fun deleteEmployee(@Path("id") id: String): Call<ModelDeleteEmployee>
}