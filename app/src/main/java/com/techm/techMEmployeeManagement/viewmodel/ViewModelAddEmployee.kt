package com.techm.techMEmployeeManagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.techm.techMEmployeeManagement.callback.ResponseCallback
import com.techm.techMEmployeeManagement.model.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.model.ModelEmployeeServerResponse
import com.techm.techMEmployeeManagement.repository.RepositoryViewModel
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import org.jetbrains.annotations.NotNull

class ViewModelAddEmployee(@NotNull application: Application) :
    AndroidViewModel(application), ResponseCallback<ModelEmployeeServerResponse> {
    private var repositoryViewModel: RepositoryViewModel = RepositoryViewModel()
    var mEmployeeResponse: MutableLiveData<ModelEmployeeServerResponse> =
        MutableLiveData<ModelEmployeeServerResponse>()
    /**
     * Calling API
     */
    fun insertEmployee(employeeInformation: ModelEmployeeRegistration) {
        repositoryViewModel.insertEmployee(employeeInformation, this)
    }

    /**
     * API success response
     * */

    override fun onSuccess(data: ModelEmployeeServerResponse) {
        mEmployeeResponse.value = data
    }

    /**
     * API failure response
     * */
    override fun onError(error: String?) {
        mEmployeeResponse.value =
            ModelEmployeeServerResponse(
                error.toString(),
                ResponseStatus.FAIL,
                ModelEmployeeRegistration("", "", "")
            )
    }


}