package com.techm.techMEmployeeManagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.techm.techMEmployeeManagement.model.ModelEmployee
import com.techm.techMEmployeeManagement.model.ModelProjects
import com.techm.techMEmployeeManagement.repository.RepositoryViewModel
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class ViewModelProject(@NotNull application: Application) :
    AndroidViewModel(application) {
    lateinit var modelProject:ModelProject
    private var repositoryViewModel: RepositoryViewModel = RepositoryViewModel(application)
    var mProjectData: LiveData<List<ModelProject>> =
        repositoryViewModel.getAllProjectData()

    var mProjectStatus: MutableLiveData<ModelProjects> =
        MutableLiveData<ModelProjects>()


    fun insertProject(projectName:String) = viewModelScope.launch {
        modelProject=ModelProject(0,projectName)
        if (repositoryViewModel.insertProject(modelProject) > 0)
            mProjectStatus.value = ModelProjects("", ResponseStatus.SUCCESS)
        else
            mProjectStatus.value = ModelProjects("", ResponseStatus.FAIL)
    }
}