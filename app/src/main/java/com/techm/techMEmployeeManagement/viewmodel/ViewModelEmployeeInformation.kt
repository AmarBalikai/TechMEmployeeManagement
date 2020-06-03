package com.techm.techMEmployeeManagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.techm.techMEmployeeManagement.callback.ResponseCallback
import com.techm.techMEmployeeManagement.model.ModelDeleteEmployee
import com.techm.techMEmployeeManagement.model.ModelServerResponse
import com.techm.techMEmployeeManagement.repository.RepositoryViewModel
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class ViewModelEmployeeInformation(@NotNull application: Application) :
    AndroidViewModel(application) {
    private var repositoryViewModel: RepositoryViewModel = RepositoryViewModel(application)
    var mEmployeeInformationData: LiveData<List<ModelEmployeeRegistration>> =
        repositoryViewModel.getAllEmployeeData()
    var mEmployeeDeleteStatus: MutableLiveData<ModelDeleteEmployee> =
        MutableLiveData<ModelDeleteEmployee>()
    fun getEmployeeInformation() {
        repositoryViewModel.getAllEmployeeData()

    }

    /**
     * Delete employee API
     * */
    fun deleteEmployee(mModelEmployeeRegistration: ModelEmployeeRegistration) =
        viewModelScope.launch {
            repositoryViewModel.deleteEmployee(mModelEmployeeRegistration)
        }
}