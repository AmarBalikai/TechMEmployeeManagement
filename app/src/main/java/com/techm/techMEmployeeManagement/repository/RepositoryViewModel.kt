package com.techm.techMEmployeeManagement.repository

import com.techm.techMEmployeeManagement.callback.ResponseCallback
import com.techm.techMEmployeeManagement.model.ModelDeleteEmployee
import com.techm.techMEmployeeManagement.model.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.model.ModelEmployeeServerResponse
import com.techm.techMEmployeeManagement.model.ModelServerResponse
import com.techm.techMEmployeeManagement.network.ApiClient
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel {
    /**
     * Method for API call
     * */
    fun retrieveBlogData(objCallback: ResponseCallback<Any>) {
        var listResponse: ModelServerResponse;

        val data: Call<ModelServerResponse>? = ApiClient.build()?.getEmployeeInformation()
        val enqueue = data?.enqueue(object : Callback<ModelServerResponse> {
            override fun onResponse(
                call: Call<ModelServerResponse>,
                response: Response<ModelServerResponse>
            ) {
                if (response.isSuccessful) {

                    listResponse = response.body()!!
                    listResponse.status = ResponseStatus.SUCCESS
                    /**
                     * Send success response to viewModel using this callback
                     */
                    objCallback.onSuccess(listResponse)
                }
            }

            override fun onFailure(call: Call<ModelServerResponse>, t: Throwable) {
                /**
                 * Send failure response to viewModel
                 */
                objCallback.onError(t.message)
            }
        })
    }

    /**
     * This method for delete the employee
     * */
    fun deleteEmployee(employeeID: String, objCallback: ResponseCallback<Any>) {
        var listResponse: ModelDeleteEmployee;

        val data: Call<ModelDeleteEmployee>? = ApiClient.build()?.deleteEmployee(employeeID)
        val enqueue = data?.enqueue(object : Callback<ModelDeleteEmployee> {
            override fun onResponse(
                call: Call<ModelDeleteEmployee>,
                response: Response<ModelDeleteEmployee>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status.equals("success")) {
                        listResponse = response.body()!!
                        listResponse.apiStatus = ResponseStatus.SUCCESS
                        /**
                         * Send success response to viewModel using this callback
                         */
                        objCallback.onSuccess(listResponse)
                    } else {
                        objCallback.onError(response.body()?.message)
                    }
                }
            }

            override fun onFailure(call: Call<ModelDeleteEmployee>, t: Throwable) {
                /**
                 * Send failure response to viewModel
                 */
                objCallback.onError(t.message)
            }
        })
    }

    /**
     * This method for insert employee
     * */
    fun insertEmployee(
        employeeData: ModelEmployeeRegistration,
        objCallback: ResponseCallback<ModelEmployeeServerResponse>
    ) {
        var listResponse: ModelEmployeeServerResponse;
        val data: Call<ModelEmployeeServerResponse>? =
            ApiClient.build()?.insertEmployee(employeeData)
        val enqueue = data?.enqueue(object : Callback<ModelEmployeeServerResponse> {
            override fun onResponse(
                call: Call<ModelEmployeeServerResponse>,
                response: Response<ModelEmployeeServerResponse>
            ) {
                if (response.isSuccessful) {

                    listResponse = response.body()!!
                    listResponse.status = ResponseStatus.SUCCESS
                    /**
                     * Send success response to viewModel using this callback
                     */
                    objCallback.onSuccess(listResponse)
                }
            }

            override fun onFailure(call: Call<ModelEmployeeServerResponse>, t: Throwable) {
                /**
                 * Send failure response to viewModel
                 */
                objCallback.onError(t.message)
            }
        })
    }
}
